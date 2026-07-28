package com.james.LMS.facade.impl;

import com.james.LMS.entity.Bucket;
import com.james.LMS.entity.Session;
import com.james.LMS.entity.Video;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.VideoUploadNotFoundCurriculumException;
import com.james.LMS.exception.VideoUploadNotFoundSessionException;
import com.james.LMS.facade.ConsumerCreateNewVideFacade;
import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateVideoPayload;
import com.james.LMS.service.BucketService;
import com.james.LMS.service.CurriculumService;
import com.james.LMS.service.SessionService;
import com.james.LMS.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerCreateNewVideoFacadeImpl implements ConsumerCreateNewVideFacade {
  private final SessionService sessionService;
  private final VideoService videoService;
  private final CurriculumService curriculumService;
  private final BucketService bucketService;

  @Override
  @RabbitHandler
  @RabbitListener(queues = {"${rabbitmq.queue-uploading-video}"})
  public void consume(BaseMessage<CreateVideoPayload> createVideosBaseMessage) {
    log.info("Consumed message {}", createVideosBaseMessage);

    CreateVideoPayload payload = createVideosBaseMessage.getPayload();

    try {
      Bucket bucket =
          this.bucketService
              .findByActive()
              .orElseThrow(() -> new EntityNotFoundException(ErrorCode.BUCKET_NOT_FOUND));

      Video video =
          Video.builder()
              .durationSeconds(payload.getDurationSeconds())
              .view(0)
              .videoUrl(payload.getVideoUrl()) // remove when update db
              .index(payload.getIndex())
              .name(payload.getName())
              .isPreview(payload.getIsPreView())
              .identifyCode(payload.getIdentifyCode())
              .bucket(bucket)
              .build();

      boolean isExistsCurriculumById =
          this.curriculumService.isExistsById(payload.getCurriculumId());
      if (!isExistsCurriculumById) throw new VideoUploadNotFoundCurriculumException();

      Session session =
          this.sessionService
              .findById(payload.getSessionId())
              .orElseThrow(VideoUploadNotFoundSessionException::new);
      video.addSession(session);

      videoService.save(video);
    } catch (VideoUploadNotFoundCurriculumException e) {
      log.error("Create video error cause by curriculum not found by email={}", payload.getEmail());
    } catch (VideoUploadNotFoundSessionException e) {
      log.error("Create video error cause by session not found by email={}", payload.getEmail());
    } catch (EntityNotFoundException e) {
      log.error("Create video error cause by bucket not found by email={}", payload.getEmail());
    }
  }
}
