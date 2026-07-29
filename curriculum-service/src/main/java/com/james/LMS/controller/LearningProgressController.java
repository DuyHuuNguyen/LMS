package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.LearningProgressFacade;
import com.james.LMS.request.CollectLearningTimeRequest;
import com.james.LMS.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/learing-progresses")
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
}
