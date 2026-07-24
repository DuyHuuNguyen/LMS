package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.VideoFacade;
import com.james.LMS.request.UpdateSessionVideoRequest;
import com.james.LMS.request.UpsertMetadataVideoRequest;
import com.james.LMS.request.VideoStreamingPresignRequest;
import com.james.LMS.request.VideoUploadingPresignUrlRequest;
import com.james.LMS.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
@Validated
public class VideoController {
  private final VideoFacade videoFacade;

  @PostMapping()
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Video APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
  public BaseResponse<Void> createMetadataVideo(@RequestBody @Valid UpsertMetadataVideoRequest request){
    return this.videoFacade.createVideoMetedata(request);
  }

  @PostMapping("/presign-url-video-streaming")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Video APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<String> generateVideoStreamingPresignUrl(
      @RequestBody @Valid VideoStreamingPresignRequest request) {
    return this.videoFacade.generateVideoStreamingPresignUrl(request);
  }

  @PostMapping("/presign-url-video-uploading")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Video APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
  public BaseResponse<String> generateUploadingPresignUrl(
      @RequestBody @Valid VideoUploadingPresignUrlRequest request) {
    return this.videoFacade.generateVideoUploadPresignUrl(request);
  }

  @PatchMapping("/session/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"Video APIs"},
      summary = "Change session of video by id")
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
  public BaseResponse<Void> changeSessionVideo(
      @PathVariable Long id, @RequestBody UpdateSessionVideoRequest request) {
    request.withId(id);
    return this.videoFacade.changeSessionVideo(request);
  }

  @PostMapping("/admin-system/gen-presign-streaming")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"Video APIs"},
      summary = "api demo")
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<String> genPresignStreamingVideo() {
    return this.videoFacade.genPresignStreamingVideo("");
  }
}
