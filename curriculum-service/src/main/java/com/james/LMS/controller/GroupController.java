package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.GroupFacade;
import com.james.LMS.request.DashBoardRequest;
import com.james.LMS.request.UpsertTrainingSessionRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.DashBoardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
  private final GroupFacade groupFacade;

  @PostMapping("/training-session/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Group APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> addTrainingSession(
      @PathVariable("id") Long id, @RequestBody @Valid UpsertTrainingSessionRequest request) {
    request.withGroupId(id);
    return this.groupFacade.createTrainingSession(request);
  }

  @GetMapping("/dashboard/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Group APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<DashBoardResponse> dashBoard(
      @PathVariable("id") Long id, @Valid DashBoardRequest request) {
    request.withGroupId(id);
    return this.groupFacade.dashBoard(request);
  }
}
