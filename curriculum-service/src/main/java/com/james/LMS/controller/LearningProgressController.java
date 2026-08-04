package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.LearningProgressFacade;
import com.james.LMS.request.CollectLearningTimeRequest;
import com.james.LMS.request.LearningProgressRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.LearningProgressResponse;
import com.james.LMS.response.SlicePaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/learning-progresses")
@RequiredArgsConstructor
public class LearningProgressController {
  private final LearningProgressFacade learningProgressFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Tracking APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> collectLearningTime(
      @RequestBody @Valid CollectLearningTimeRequest request) {
    return this.learningProgressFacade.collectLearningTime(request);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Tracking APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<SlicePaginationResponse<LearningProgressResponse>> findLearningProgress(
      @NotNull LearningProgressRequest request) {
    return this.learningProgressFacade.findLearningProgress(request);
  }
}
