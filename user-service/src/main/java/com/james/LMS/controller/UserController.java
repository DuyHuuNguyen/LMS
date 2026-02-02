package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.UserFacade;
import com.james.LMS.request.LoginRequest;
import com.james.LMS.request.RefreshTokenRequest;
import com.james.LMS.request.UpsertUserRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.LoginResponse;
import com.james.LMS.response.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @PostMapping("/sign-up")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Sign up to system by email, user name and password",
      tags = {"User APIs"})
  public BaseResponse<Void> signUp(@Valid @RequestBody UpsertUserRequest upsertUserRequest) {
    return this.userFacade.signUp(upsertUserRequest);
  }

  @PostMapping("/refresh-token")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"User APIs"})
  public BaseResponse<RefreshTokenResponse> refreshToken(
      @RequestBody RefreshTokenRequest refreshTokenRequest) {
    return this.userFacade.refreshToken(refreshTokenRequest);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auth APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> logout() {
    return this.userFacade.logout();
  }
}
