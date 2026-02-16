package com.james.LMS.service.impl;

import com.james.LMS.entity.Exam;
import com.james.LMS.entity.Test;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateTestsPayload;
import com.james.LMS.service.ConsumerCreateNewExamService;
import com.james.LMS.service.ExamService;
import com.james.LMS.service.TestService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerCreateNewExamServiceImpl implements ConsumerCreateNewExamService {

  private final ExamService examService;
  private final TestService testService;

  @Override
  @RabbitHandler
  @RabbitListener(queues = {"${rabbitmq.create-tests-queue}"})
  public void consume(BaseMessage<CreateTestsPayload> createTestsBaseMessage) {
    try {
      Exam exam =
          this.examService
              .findByIdAndIsActiveIsTrue(createTestsBaseMessage.getPayload().getExamId())
              .orElseThrow(() -> new EntityNotFoundException(ErrorCode.EXAM_NOT_FOUND));

      List<Test> tests =
          createTestsBaseMessage.getPayload().getCreateTestDTOS().stream()
              .map(
                  createTestDTO ->
                      Test.builder()
                          .index(createTestDTO.getIndex())
                          .question(createTestDTO.getQuestion())
                          .chooses(createTestDTO.getChooses())
                          .answer(createTestDTO.getAnswer())
                          .exam(exam)
                          .build())
              .peek(test -> log.info("test in for {} ", test))
              .collect(Collectors.toUnmodifiableList());

      this.testService.saveAll(tests);
    } catch (Exception e) {
      log.error("message error {}", createTestsBaseMessage);
      log.error("{}", e.getMessage());
    }
  }
}
