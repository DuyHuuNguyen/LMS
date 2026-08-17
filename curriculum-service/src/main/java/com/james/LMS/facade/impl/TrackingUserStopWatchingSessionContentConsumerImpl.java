package com.james.LMS.facade.impl;

import com.james.LMS.dto.ValidatePurchasedCurriculumAndContainSessionAndSessionContentDTO;
import com.james.LMS.entity.LastestWatchingVideo;
import com.james.LMS.entity.Session;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.facade.TrackingUserStopWatchingSessionContentConsumer;
import com.james.LMS.message.final_lms_message.StopWatchingSessionContentMessage;
import com.james.LMS.service.CurriculumService;
import com.james.LMS.service.CurriculumValidatorService;
import com.james.LMS.service.LastestWatchingVideoService;
import com.james.LMS.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingUserStopWatchingSessionContentConsumerImpl
    implements TrackingUserStopWatchingSessionContentConsumer {

  private final LastestWatchingVideoService lastestWatchingVideoService;
  private final CurriculumService curriculumService;
  private final SessionService sessionService;
  private final CurriculumValidatorService curriculumValidatorService;


  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  @RabbitHandler
  @RabbitListener(queues = {"${rabbitmq.user-stop-watching-content-session.video-queue-name}"})
  public void handleStopWatchingVideo(StopWatchingSessionContentMessage message) {
    this.logReceiveMessage(message);

    ValidatePurchasedCurriculumAndContainSessionAndSessionContentDTO
        validatePurchasedCurriculumAndContainSessionAndSessionContentDTO =
            ValidatePurchasedCurriculumAndContainSessionAndSessionContentDTO.builder()
                .sessionContentId(message.getContentId())
                .userId(message.getUserId())
                .sessionId(message.getSessionId())
                .curriculumId(message.getCurriculumId())
                .build();

    boolean isUserPurchasedCurriculumWithVideoInSession =
        this.curriculumValidatorService.isPurchasedCurriculumWithVideoInSession(
            validatePurchasedCurriculumAndContainSessionAndSessionContentDTO);

    if (!isUserPurchasedCurriculumWithVideoInSession) {
      log.warn("User dont Purchase Curriculum With Video In Session)");
      return;
    }
    try {
      saveLastWatchingContent(message);
    } catch (EntityNotFoundException entityNotFoundException) {
      log.error("Dont save Last watching content type video");
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  @RabbitHandler
  @RabbitListener(queues = {"${rabbitmq.user-stop-watching-content-session.exam-queue-name}"})
  public void handleStopExam(StopWatchingSessionContentMessage message) {
    this.logReceiveMessage(message);

    ValidatePurchasedCurriculumAndContainSessionAndSessionContentDTO
        validatePurchasedCurriculumAndContainSessionAndSessionContentDTO =
            ValidatePurchasedCurriculumAndContainSessionAndSessionContentDTO.builder()
                .sessionContentId(message.getContentId())
                .userId(message.getUserId())
                .sessionId(message.getSessionId())
                .curriculumId(message.getCurriculumId())
                .build();

    boolean isUserPurchasedCurriculumWithExamInSession =
        this.curriculumValidatorService.isPurchasedCurriculumWithExamInSession(
            validatePurchasedCurriculumAndContainSessionAndSessionContentDTO);

    if (!isUserPurchasedCurriculumWithExamInSession) {
      log.warn("User dont Purchase Curriculum With Exam In Session)");
      return;
    }
    try {
      saveLastWatchingContent(message);
    } catch (EntityNotFoundException entityNotFoundException) {
      log.error("Dont save Last watching content type exam");
    }
  }

  private void saveLastWatchingContent(StopWatchingSessionContentMessage message) {
    Session session =
        this.sessionService
            .findAndFetchCurriculumById(message.getSessionId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SESSION_NOT_FOUND));

    this.lastestWatchingVideoService.disableActiveCurrentWatchSessionContent(
        message.getUserId(), message.getCurriculumId());

    LastestWatchingVideo lastestWatchingVideo =
        LastestWatchingVideo.builder()
            .curriculum(session.getCurriculum())
            .session(session)
            .contentId(message.getContentId())
            .userId(message.getUserId())
            .pausedAt(message.getPausedAt())
            .contentType(message.getContentType())
            .build();

    this.lastestWatchingVideoService.save(lastestWatchingVideo);
  }

  private void logReceiveMessage(StopWatchingSessionContentMessage message) {
    log.info("Receive message {}", message.toJsonString());
  }
}
