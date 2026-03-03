package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.ValidateVideoAccessDTO;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.ObjectStorageEnum;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.facade.VideoFacade;
import com.james.LMS.request.VideoStreamingPresignRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.service.CurriculumService;
import com.james.LMS.service.CurriculumValidatorService;
import com.james.LMS.service.MinioService;
import com.james.LMS.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoFacadeImpl implements VideoFacade {
  private final VideoService videoService;
  private final MinioService minioService;
  private final CurriculumService curriculumService;
  private final CurriculumValidatorService curriculumValidatorService;

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
      String fileName = String.format(ObjectStorageEnum.VIDEO.getContent(), request.getVideoId());
      String presignUrl =
          this.minioService.generatePresignedVideoStreamingUrl(fileName, durationSecondsOfVideo);
      return BaseResponse.build(presignUrl, true);
    } catch (RuntimeException e) {
      throw new EntityNotFoundException(ErrorCode.VIDEO_NOT_FOUND_AT_STORAGE);
    }
  }
}
