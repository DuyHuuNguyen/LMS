package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.ExamFacade;
import com.james.LMS.request.AddNewExamRequest;
import com.james.LMS.request.ExamDetailRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ExamDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
public class ExamController {
  private final ExamFacade examFacade;

  @GetMapping("/detail/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Exam APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_USER')||hasRole('ROLE_INSTRUCTOR')")
  public BaseResponse<ExamDetailResponse> findDetailById(
      @PathVariable Long id, ExamDetailRequest examDetailRequest) {
    examDetailRequest.withId(id);
    return this.examFacade.findExamDetail(examDetailRequest);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Exam APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
  public BaseResponse<Void> addNewExam(@RequestBody @Valid AddNewExamRequest addNewExamRequest) {
    return this.examFacade.addNewExam(addNewExamRequest);
  }
}
