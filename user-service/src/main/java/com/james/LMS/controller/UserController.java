package com.james.LMS.controller;

import com.james.LMS.facade.UserFacade;
import com.james.LMS.request.LoginRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserFacade userFacade;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Login to system by email and password",
      tags = {"User APIs"})
  private BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    return this.userFacade.login(loginRequest);
  }
}
