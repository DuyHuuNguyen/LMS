package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.UserFacade;
import com.james.LMS.request.*;
import com.james.LMS.response.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserFacade userFacade;

  @Hidden
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Login to system by email and password",
      tags = {"User APIs"})
  public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
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
  @Operation(tags = {"User APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> logout() {
    return this.userFacade.logout();
  }

  @PostMapping("/reset-password")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"User APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> resetPassword(
      @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
    return this.userFacade.resetPassword(resetPasswordRequest);
  }

  @PostMapping("/forgot-password")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"User APIs"})
  public BaseResponse<ForgotPasswordResponse> forgotPassword(
      @Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
    return this.userFacade.forgotPassword(forgotPasswordRequest);
  }

  @PostMapping("/verify-otp")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"User APIs"})
  public BaseResponse<VerifyOTPResponse> verify(
      @Valid @RequestBody VerifyOTPRequest verifyOTPRequest) {
    return this.userFacade.verify(verifyOTPRequest);
  }

  @PostMapping("/instructor")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"User APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_USER')")
  public BaseResponse<Void> instruct(@Valid @RequestBody InstructionRequest instructionRequest) {
    return this.userFacade.instruct(instructionRequest);
  }

  @GetMapping("/profile")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"User APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_USER')")
  public BaseResponse<UserDetailResponse> findProfile() {
    return this.userFacade.findProfile();
  }

  @GetMapping("/detail/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"User APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_USER')")
  public BaseResponse<UserDetailResponse> findDetailById(@PathVariable Long id) {
    return this.userFacade.findDetailById(id);
  }

  @PostMapping(value = "/avatar", consumes = "multipart/form-data", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Upload image",
      tags = {"User APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  @SneakyThrows
  public BaseResponse<String> uploadImage(@RequestPart("image") MultipartFile image) {
    return this.userFacade.uploadFile(image.getBytes());
  }

  @PutMapping("/profile")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"User APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_USER')")
  public BaseResponse<Void> updateProfile(
      @RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
    return this.userFacade.updateProfile(updateUserProfileRequest);
  }
}
