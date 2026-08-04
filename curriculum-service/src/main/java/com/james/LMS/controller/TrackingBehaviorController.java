package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.TrackingBehaviorFacade;
import com.james.LMS.request.StoppedWatchingContentRequest;
import com.james.LMS.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tracking-behaviors")
public class TrackingBehaviorController {
  private final TrackingBehaviorFacade trackingBehaviorFacade;

  @PostMapping("/stopping-watching")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"Tracking APIs"},
      summary = "api demo")
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> trackingStoppedWatchingContent(
      @RequestBody StoppedWatchingContentRequest request) {
    return this.trackingBehaviorFacade.trackingStoppedWatchingContent(request);
  }
}
