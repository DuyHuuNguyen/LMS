package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.CurriculumFacade;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.CurriculumReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/curriculums")
@RequiredArgsConstructor
public class CurriculumController {

  private final CurriculumFacade curriculumFacade;

  @GetMapping("/review/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_USER')")
  public BaseResponse<CurriculumReviewResponse> reviewCurriculum(@PathVariable Long id) {
    return curriculumFacade.findCurriculumForReviewById(id);
  }
}
