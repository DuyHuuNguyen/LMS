package com.james.LMS.facade.impl;

import com.james.LMS.entity.LearningProgress;
import com.james.LMS.entity.UserCurriculum;
import com.james.LMS.facade.LearningProgressConsumerFacade;
import com.james.LMS.message.final_lms_message.LearningProgressMessage;
import com.james.LMS.service.LearningProgressService;
import com.james.LMS.service.UserCurriculumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearningProgressConsumerFacadeImpl implements LearningProgressConsumerFacade {
  private final UserCurriculumService userCurriculumService;
  private final LearningProgressService learningProgressService;

  /**
   * Consumes a learning-progress message generated after a user finishes watching a video.
   *
   * <p>Business flow:
   *
   * <ol>
   *   <li>Find the existing {@link LearningProgress} of the user in the curriculum.
   *   <li>If no progress exists, create a new one with the received learning minutes.
   *   <li>Verify that the video in the message actually belongs to the user's curriculum.
   *   <li>If this is a newly created progress, associate it with the corresponding {@link
   *       UserCurriculum}.
   *   <li>If the message references a video outside of the curriculum, or references a different
   *       {@link UserCurriculum} than the existing progress, reject the message to prevent invalid
   *       updates.
   *   <li>Accumulate the learning time and persist the updated progress.
   * </ol>
   *
   * <p>The validation against {@link UserCurriculum} is important because messages are
   * asynchronous. It prevents accidental or malicious updates caused by an invalid
   * curriculum/content relationship.
   *
   * @param message RabbitMQ message containing user id, curriculum id, video id, learning minutes
   *     and request tracing information.
   */
  @Override
  @Transactional
  @RabbitListener(queues = "${rabbitmq.learning-progress.queues.queue-video}")
  public void handleVideo(LearningProgressMessage message) {
    this.logJsonMessage(message);

    LearningProgress learningProgress =
        this.learningProgressService
            .findByUserIdAndCurriculumId(message.getUserId(), message.getCurriculumId()) // true
            .orElseGet(
                () ->
                    LearningProgress.builder()
                        .learningMinutes(message.getLearningMinutes())
                        .build());

    UserCurriculum userCurriculum =
        this.userCurriculumService
            .findByUserIdAndCurriculumIdAndVideoId(
                message.getContentId(), message.getUserId(), message.getCurriculumId())
            .orElse(null);

    if (learningProgress.isEmptyUserCurriculum()) {
      learningProgress.addUserCurriculum(userCurriculum);
    }

    boolean shouldRejectMessageBecauseVideoNotInCurriculum =
        userCurriculum == null
            || (!learningProgress.isEmptyUserCurriculum()
                && !learningProgress.equal(userCurriculum));
    if (shouldRejectMessageBecauseVideoNotInCurriculum) {
      log.warn(
          "The learningProgressMessage was rejected by curriculum not contain the video with id ={}",
          message.getContentId());
      return;
    }

    learningProgress.addLearningMinutes(message.getLearningMinutes());

    this.learningProgressService.save(learningProgress);

    this.logCompleteMessage(message.getRequestId());
  }

  /**
   * Consumes a learning-progress message generated after a user completes an exam.
   *
   * <p>This method follows the same validation strategy as {@code handleVideo()}, but verifies the
   * relationship using the exam identifier instead of the video identifier.
   *
   * <p>Business flow:
   *
   * <ol>
   *   <li>Load or create the user's {@link LearningProgress} for the curriculum.
   *   <li>Verify that the exam belongs to the user's curriculum.
   *   <li>Associate the progress with the {@link UserCurriculum} if it is newly created.
   *   <li>Reject the message if the exam does not belong to the curriculum or the existing progress
   *       references another curriculum.
   *   <li>Add the received learning minutes and save the updated progress.
   * </ol>
   *
   * <p>This validation guarantees that asynchronous exam events cannot update learning progress
   * belonging to another curriculum.
   *
   * @param message RabbitMQ message containing user id, curriculum id, exam id, learning minutes
   *     and request tracing information.
   */
  @Override
  @Transactional
  @RabbitListener(queues = "${rabbitmq.learning-progress.queues.queue-exam}")
  public void handleExam(LearningProgressMessage message) {
    this.logJsonMessage(message);

    LearningProgress learningProgress =
        this.learningProgressService
            .findByUserIdAndCurriculumId(message.getUserId(), message.getCurriculumId())
            .orElseGet(
                () ->
                    LearningProgress.builder()
                        .learningMinutes(message.getLearningMinutes())
                        .build());

    UserCurriculum userCurriculum =
        this.userCurriculumService
            .findByUserIdAndCurriculumIdAndExamId(
                message.getContentId(), message.getUserId(), message.getCurriculumId())
            .orElse(null);

    if (learningProgress.isEmptyUserCurriculum()) {
      learningProgress.addUserCurriculum(userCurriculum);
    }

    boolean shouldRejectMessageBecauseVideoNotInCurriculum =
        userCurriculum == null
            || (!learningProgress.isEmptyUserCurriculum()
                && !learningProgress.equal(userCurriculum));

    if (shouldRejectMessageBecauseVideoNotInCurriculum) {
      log.warn(
          "The learningProgressMessage was rejected because curriculum does not contain exam with id={}",
          message.getContentId());
      return;
    }

    learningProgress.addLearningMinutes(message.getLearningMinutes());

    this.learningProgressService.save(learningProgress);

    this.logCompleteMessage(message.getRequestId());
  }

  private void logJsonMessage(LearningProgressMessage message) {
    log.info("Receive message : {}", message.toJsonString());
  }

  private void logCompleteMessage(String requestId) {
    log.info("Complete handle xRequestId = {}", requestId);
  }
}
