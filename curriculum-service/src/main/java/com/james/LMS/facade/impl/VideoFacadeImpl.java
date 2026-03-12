package com.james.LMS.facade.impl;

import com.james.LMS.config.MinioConfig;
import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.ValidChangeSessionVideoAccessDTO;
import com.james.LMS.dto.ValidVideoUploadingAccessDTO;
import com.james.LMS.dto.ValidateVideoAccessDTO;
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
import com.james.LMS.request.VideoStreamingPresignRequest;
import com.james.LMS.request.VideoUploadingPresignUrlRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.service.*;
import com.james.LMS.util.HashMD5Util;
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
  private static final String DOT_MP4 = ".mp4";

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

    String identifyCode =
        String.format(
            IdentifyTemplate.IDENTIFY_CODE_TEMPLATE.getTemplate(),
            principal.getUsername(),
            HashMD5Util.encryptMd5(request.getVideoName()));
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
        CompletableFuture.supplyAsync(
            () ->
                this.videoService
                    .findVideoAndFetchSessionById(request.getId())
                    .orElseThrow(
                        () -> new EntityNotFoundException(ErrorCode.VIDEO_METADATA_NOT_FOUND)));

    CompletableFuture<Session> sessionFuture =
        CompletableFuture.supplyAsync(
            () ->
                this.sessionService
                    .findById(request.getNewSessionId())
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SESSION_NOT_FOUND)));
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
            "pWF2AmF0GwAAAZyo5AxrYWYAYXUCYXMaACWwUQ.mp4", 100000);
    return BaseResponse.build(presignUrl, true);
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
