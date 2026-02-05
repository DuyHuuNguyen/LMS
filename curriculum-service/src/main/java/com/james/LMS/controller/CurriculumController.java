package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/curriculums")
public class CurriculumController {

  @GetMapping("/demo")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_USER')")
  public BaseResponse<Void> demo() {
    return BaseResponse.ok();
  }
}
