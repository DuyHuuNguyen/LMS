package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.TopicFacade;
import com.james.LMS.request.AddPersonalFollowedTopicsRequest;
import com.james.LMS.request.AllTopicRequest;
import com.james.LMS.request.PersonalFollowedTopicsRequest;
import com.james.LMS.request.UnfollowedTopicsRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.PaginationResponse;
import com.james.LMS.response.TopicResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor
public class TopicController {
  private final TopicFacade topicFacade;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"TOPIC APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<TopicResponse>> findPersonalFollowedTopics(
    @Valid  PersonalFollowedTopicsRequest personalFlowedTopicsRequest) {
    return this.topicFacade.findPersonalFollowedTopics(personalFlowedTopicsRequest);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"TOPIC APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> followTopics(
      @RequestBody @Valid AddPersonalFollowedTopicsRequest addPersonalFlowedTopicsRequest) {
    return this.topicFacade.followTopics(addPersonalFlowedTopicsRequest);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"TOPIC APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> unfollowedTopics(
      @RequestBody @Valid UnfollowedTopicsRequest unfollowedTopicsRequest) {
    return this.topicFacade.unfollowTopics(unfollowedTopicsRequest);
  }

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"TOPIC APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<TopicResponse>> findAll(
          @Valid  @NotNull AllTopicRequest allTopicRequest) {
    return this.topicFacade.findAll(allTopicRequest);
  }
}
