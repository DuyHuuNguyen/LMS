package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.CurriculumFacade;
import com.james.LMS.request.CurriculumByTopicRequest;
import com.james.LMS.request.CurriculumHomeRequest;
import com.james.LMS.request.TopicCriteria;
import com.james.LMS.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

  @GetMapping("/home")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<CurriculumHomeResponse> findCurriculumForHome(
      @Valid CurriculumHomeRequest curriculumHomeRequest) {
    return this.curriculumFacade.findCurriculumForHomeNewFlow(curriculumHomeRequest);
  }

  @GetMapping("/topics")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<TopicResponse>> findAllTopics(
      @Valid TopicCriteria topicCriteria) {
    return this.curriculumFacade.findAllTopicByCriteria(topicCriteria);
  }

  @GetMapping("/sub-catagoy-curriculums")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<CurriculumResponse>> findCurriculumByTopicId(
      CurriculumByTopicRequest curriculumByTopicRequest) {
    return this.curriculumFacade.findCurriculumByTopicId(curriculumByTopicRequest);
  }
}
