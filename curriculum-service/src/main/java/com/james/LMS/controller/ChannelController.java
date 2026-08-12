package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.ChannelFacade;
import com.james.LMS.request.ChannelCurriculumsRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ChannelCurriculumResponse;
import com.james.LMS.response.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channels")
@RequiredArgsConstructor
public class ChannelController {
  private final ChannelFacade channelFacade;

  @GetMapping("/curriculums/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Channel APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<ChannelCurriculumResponse>> findAllCurriculumsInChannel(
      @PathVariable("id") Long id, ChannelCurriculumsRequest request) {
    request.setChannelId(id);
    return this.channelFacade.findAllCurriculumsInChannel(request);
  }

  @GetMapping("/internal/channel-id")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"Channel APIs"},
      summary = "Only get id of channel")
  public Long findChannelIdByUserId(@RequestParam("userId") Long userId) {
    return this.channelFacade.findChannelIdByUserId(userId);
  }
}
