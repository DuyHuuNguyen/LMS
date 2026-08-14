package com.james.LMS.facade.impl;

import com.james.LMS.config.MinioConfig;
import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.ValidChangeSessionVideoAccessDTO;
import com.james.LMS.dto.ValidInstructorHoldVideoDTO;
import com.james.LMS.dto.ValidVideoUploadingAccessDTO;
import com.james.LMS.dto.ValidateVideoAccessDTO;
import com.james.LMS.entity.LastestWatchingVideo;
import com.james.LMS.entity.Session;
import com.james.LMS.entity.Video;
import com.james.LMS.enums.*;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.exception.VideoAlreadyExistInStorageException;
import com.james.LMS.facade.VideoFacade;
import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateVideoPayload;
import com.james.LMS.request.UpdateSessionVideoRequest;
import com.james.LMS.request.UpsertMetadataVideoRequest;
import com.james.LMS.request.VideoStreamingPresignRequest;
import com.james.LMS.request.VideoUploadingPresignUrlRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.PresignUrlResponse;
import com.james.LMS.service.*;
import com.james.LMS.util.IdentifyCodeOfVideoUtil;
import com.james.LMS.util.SecurityUserDetailsUtil;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoFacadeImpl implements VideoFacade {
  private final VideoService videoService;
  private final MinioService minioService;
  private final MinioConfig minioConfig;
  private final CurriculumService curriculumService;
  private final CurriculumValidatorService curriculumValidatorService;
  private final ProducerCreateVideoService producerCreateVideoService;
  private final SessionService sessionService;
  private final LastestWatchingVideoService lastestWatchingVideoService;
  private static final String DOT_MP4 = ".mp4";
  private static final int ZERO = 0;

  @Override
  public BaseResponse<PresignUrlResponse> generateVideoStreamingPresignUrl(
      VideoStreamingPresignRequest request) {

    ValidateVideoAccessDTO validateVideoAccessDTO =
        ValidateVideoAccessDTO.builder()
            .userId(SecurityUserDetailsUtil.PRINCIPAL.getId())
            .curriculumId(request.getCurriculumId())
            .sessionId(request.getSessionId())
            .videoId(request.getVideoId())
            .build();

    boolean isPurchasedCurriculumToHaveVideo =
        this.curriculumValidatorService.isPurchasedCurriculumToHaveVideo(validateVideoAccessDTO);

    if (!isPurchasedCurriculumToHaveVideo)
      throw new PermissionDeniedException(ErrorCode.PERMISSION_DENIED_VIDEO);

    String presignUrl =
        this.videoService
            .generatePresignUrlToWatchVideo(request.getVideoId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.VIDEO_NOT_FOUND_AT_STORAGE));

    int pausedAt = ZERO;
    Optional<LastestWatchingVideo> lastestWatchingVideo =
        this.lastestWatchingVideoService.findByVideoId(request.getVideoId());

    if (lastestWatchingVideo.isPresent()) {
      pausedAt = lastestWatchingVideo.get().getPausedAt();
    }

    return BaseResponse.build(
        PresignUrlResponse.builder().presignUrl(presignUrl).pausedAt(pausedAt).build(), true);
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

    String identifyCode =
        IdentifyCodeOfVideoUtil.genVideoIdentifyCode(
            principal.getUsername(), request.getVideoName());

    Optional<Video> videoOptional = this.videoService.findByIdentifyCode(identifyCode);

    String videoUrl = UUID.randomUUID().toString().concat(DOT_MP4);

    boolean isUploadedVideoIntoStorage =
        videoOptional.isPresent()
            && this.minioService.isExistFile(
                this.minioConfig.getVideoBucket(), videoOptional.get().getVideoUrl());
    if (isUploadedVideoIntoStorage)
      throw new VideoAlreadyExistInStorageException(ErrorCode.VIDEO_WAS_UPLOADED_INTO_STORAGE);

    boolean isJustAlreadyExistMetadataVideo = videoOptional.isPresent();
    if (isJustAlreadyExistMetadataVideo)
      return this.generatePresignUrlForUpload(videoOptional.get().getVideoUrl());

    this.produceUploadingVideoMessage(request, videoUrl, principal.getUsername(), identifyCode);
    return this.generatePresignUrlForUpload(videoUrl);
  }

  @Override
  @Transactional
  public BaseResponse<Void> changeSessionVideo(UpdateSessionVideoRequest request) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    ValidInstructorHoldVideoDTO validInstructorHoldVideoDTO =
        ValidInstructorHoldVideoDTO.builder()
            .userId(principal.getId())
            .curriculumId(request.getCurriculumId())
            .videoId(request.getId())
            .build();
    boolean isInstructorHoldVideo =
        this.curriculumValidatorService.isInstructorHoldVideo(validInstructorHoldVideoDTO);
    if (!isInstructorHoldVideo)
      throw new EntityNotFoundException(ErrorCode.VIDEO_METADATA_NOT_FOUND);

    ValidChangeSessionVideoAccessDTO validChangeSessionVideoAccessDTO =
        ValidChangeSessionVideoAccessDTO.builder()
            .userChanelHolderId(principal.getId())
            .curriculumId(request.getCurriculumId())
            .newSessionId(request.getNewSessionId())
            .videoId(request.getId())
            .build();

    boolean isExistedChannelAndCurriculumForChangeSession =
        this.curriculumValidatorService.isExistedChannelAndCurriculumForChangeSession(
            validChangeSessionVideoAccessDTO);
    if (!isExistedChannelAndCurriculumForChangeSession)
      throw new PermissionDeniedException(ErrorCode.PERMISSION_DENIED_VIDEO);

    CompletableFuture<Video> videoFuture =
        this.videoService.findCompletableFutureVideoAndFetchSessionById(request.getId());

    CompletableFuture<Session> sessionFuture =
        this.sessionService.findCompletableFutureSessionById(request.getNewSessionId());
    try {
      CompletableFuture.allOf(videoFuture, sessionFuture).join();

      Video video = videoFuture.join();
      Session targetSessionForChanging = sessionFuture.join();
      video.addSession(targetSessionForChanging);
      this.videoService.save(video);

    } catch (EntityNotFoundException e) {
      throw new EntityNotFoundException(ErrorCode.SESSION_OR_VIDEO_NOT_FOUND);
    }
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<String> genPresignStreamingVideo(String videoName) {
    String presignUrl =
        this.minioService.generatePresignedVideoStreamingUrl(
            "Screencast from 2026-03-01 12-23-58.webm", 100000);
    return BaseResponse.build(presignUrl, true);
  }

  @Override
  public BaseResponse<Void> createVideoMetadata(UpsertMetadataVideoRequest request) {
    throw new UnsupportedOperationException("Chua code");
  }

  private BaseResponse<String> generatePresignUrlForUpload(String videoUrl) {
    String fileName = String.format(ObjectStorageEnum.VIDEO.getContent(), videoUrl);
    String presignUrl = this.minioService.generatePresignedVideoUploadUrl(fileName);
    return BaseResponse.build(presignUrl, true);
  }

  private void produceUploadingVideoMessage(
      VideoUploadingPresignUrlRequest request, String videoUrl, String email, String identifyCode) {
    CreateVideoPayload createVideoPayload =
        CreateVideoPayload.builder()
            .videoUrl(videoUrl)
            .curriculumId(request.getCurriculumId())
            .sessionId(request.getSessionId())
            .durationSeconds(request.getDurationSeconds())
            .index(request.getIndex())
            .isPreView(request.getIsPreView())
            .name(request.getVideoName())
            .email(email)
            .identifyCode(identifyCode)
            .build();

    BaseMessage<CreateVideoPayload> createVideoMessage =
        BaseMessage.<CreateVideoPayload>builder()
            .type(MessageType.CREATE_VIDEO)
            .createdAt(Instant.now())
            .source(SourceMessageEnum.CURRICULUM_SERVICE)
            .payload(createVideoPayload)
            .build();

    this.producerCreateVideoService.send(createVideoMessage);
  }
}
