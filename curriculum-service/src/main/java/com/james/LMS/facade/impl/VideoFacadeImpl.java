package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.ValidVideoUploadingAccessDTO;
import com.james.LMS.dto.ValidateVideoAccessDTO;
import com.james.LMS.entity.Video;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.MessageType;
import com.james.LMS.enums.ObjectStorageEnum;
import com.james.LMS.enums.SourceMessageEnum;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.facade.VideoFacade;
import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateVideoPayload;
import com.james.LMS.request.VideoStreamingPresignRequest;
import com.james.LMS.request.VideoUploadingPresignUrlRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.service.*;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VideoFacadeImpl implements VideoFacade {
  private final VideoService videoService;
  private final MinioService minioService;
  private final CurriculumService curriculumService;
  private final CurriculumValidatorService curriculumValidatorService;
  private final ProducerCreateVideoService producerCreateVideoService;

  @Override
  public BaseResponse<String> generateVideoStreamingPresignUrl(
      VideoStreamingPresignRequest request) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ValidateVideoAccessDTO validateVideoAccessDTO =
        ValidateVideoAccessDTO.builder()
            .userId(principal.getId())
            .curriculumId(request.getCurriculumId())
            .sessionId(request.getSessionId())
            .videoId(request.getVideoId())
            .build();

    boolean isPurchasedCurriculumToHaveVideo =
        this.curriculumValidatorService.isPurchasedCurriculumToHaveVideo(validateVideoAccessDTO);

    if (!isPurchasedCurriculumToHaveVideo)
      throw new PermissionDeniedException(ErrorCode.PERMISSION_DENIED_VIDEO);

    Integer durationSecondsOfVideo = this.videoService.findDurationById(request.getVideoId());

    try {
      Video video =
          this.videoService.findById(request.getVideoId()).orElseThrow(RuntimeException::new);
      String fileName = String.format(ObjectStorageEnum.VIDEO.getContent(), video.getVideoUrl());
      String presignUrl =
          this.minioService.generatePresignedVideoStreamingUrl(fileName, durationSecondsOfVideo);
      return BaseResponse.build(presignUrl, true);
    } catch (RuntimeException e) {
      throw new EntityNotFoundException(ErrorCode.VIDEO_NOT_FOUND_AT_STORAGE);
    }
  }

  @Override
  @Transactional
  public BaseResponse<String> generateVideoUploadPresignUrl(
      VideoUploadingPresignUrlRequest request) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ValidVideoUploadingAccessDTO validVideoUploadingAccessDTO =
        ValidVideoUploadingAccessDTO.builder()
            .userChanelHolderId(principal.getId())
            .curriculumId(request.getCurriculumId())
            .sessionId(request.getSessionId())
            .build();

    boolean isExistedChannelAndCurriculumForUploadVideo =
        this.curriculumValidatorService.isExistedChannelAndCurriculumForUploadVideo(
            validVideoUploadingAccessDTO);
    if (!isExistedChannelAndCurriculumForUploadVideo)
      throw new PermissionDeniedException(ErrorCode.UPLOADING_VIDEO_IS_DENIED);

    try {
      String videoUrl = UUID.randomUUID().toString();

      CreateVideoPayload createVideoPayload =
          CreateVideoPayload.builder()
              .videoUrl(videoUrl)
              .curriculumId(request.getCurriculumId())
              .sessionId(request.getSessionId())
              .build();

      BaseMessage<CreateVideoPayload> createVideoMessage =
          BaseMessage.<CreateVideoPayload>builder()
              .type(MessageType.CREATE_VIDEO)
              .createdAt(Instant.now())
              .source(SourceMessageEnum.CURRICULUM_SERVICE)
              .payload(createVideoPayload)
              .build();

      this.producerCreateVideoService.send(createVideoMessage);

      String fileName = String.format(ObjectStorageEnum.VIDEO.getContent(), videoUrl);
      String presignUrl = this.minioService.generatePresignedVideoUploadUrl(fileName);
      return BaseResponse.build(presignUrl, true);

    } catch (RuntimeException e) {
      throw new EntityNotFoundException(ErrorCode.VIDEO_NOT_FOUND_AT_STORAGE);
    }
  }
}
