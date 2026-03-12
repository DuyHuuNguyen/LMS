package com.james.LMS.service.impl;

import com.james.LMS.dto.InstructorDTO;
import com.james.LMS.entity.User;
import com.james.LMS.enums.InstructorEnum;
import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.LoadLecturerIntoCachePayload;
import com.james.LMS.service.ConsumerLoadLecturerIntoCacheService;
import com.james.LMS.service.UserService;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerLoadLecturerIntoCacheServiceImpl
    implements ConsumerLoadLecturerIntoCacheService {
  private final RedisTemplate<String, InstructorDTO> instructorDTORedisTemplate;
  private final UserService userService;

  private static final int TIME_OUT_OF_INSTRUCTOR_INFO = 10;

  @Override
  @RabbitHandler
  @RabbitListener(queues = {"${rabbitmq.cache-data-queue}"})
  public void consume(
      BaseMessage<LoadLecturerIntoCachePayload> loadLecturerIntoCachePayloadMessage) {
    log.info("Consume message {}", loadLecturerIntoCachePayloadMessage);
    LoadLecturerIntoCachePayload loadLecturerIntoCachePayload =
        loadLecturerIntoCachePayloadMessage.getPayload();
    Optional<User> userOptional =
        this.userService.findUserAndInstructorById(loadLecturerIntoCachePayload.getUserId());
    if (userOptional.isEmpty()) {
      log.warn("User not found by Id {}", loadLecturerIntoCachePayloadMessage);
      return;
    }
    User user = userOptional.get();
    InstructorDTO instructorDTO =
        InstructorDTO.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .avatar(user.getAvatarUrl())
            .instructorName(user.getInstructor().getName())
            .build();

    String instructorKey =
        InstructorEnum.INSTRUCTOR_KEY.getContent().concat(user.getId().toString());

    this.instructorDTORedisTemplate
        .opsForValue()
        .set(instructorKey, instructorDTO, TIME_OUT_OF_INSTRUCTOR_INFO, TimeUnit.MINUTES);
    log.info("Handle consume done {}", instructorDTO);
  }
}
