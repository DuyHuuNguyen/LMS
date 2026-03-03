package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.VideoFacade;
import com.james.LMS.request.VideoStreamingPresignRequest;
import com.james.LMS.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

  @PostMapping("/presign-url")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Video APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<String> generateVideoStreamingPresignUrl(
      @RequestBody VideoStreamingPresignRequest request) {
    return this.videoFacade.generateVideoStreamingPresignUrl(request);
  }
}
