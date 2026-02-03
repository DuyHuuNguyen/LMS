package com.james.LMS.facade;

import com.james.LMS.request.*;
import com.james.LMS.response.*;

public interface UserFacade {
  BaseResponse<LoginResponse> login(LoginRequest loginRequest);

  BaseResponse<Void> signUp(UpsertUserRequest upsertUserRequest);

  BaseResponse<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);

  BaseResponse<Void> logout();

  BaseResponse<Void> resetPassword(ResetPasswordRequest resetPasswordRequest);

  BaseResponse<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

  BaseResponse<VerifyOTPResponse> verify(VerifyOTPRequest verifyOTPRequest);

  BaseResponse<Void> instruct(InstructionRequest instructionRequest);

  BaseResponse<UserDetailResponse> findProfile();

  BaseResponse<UserDetailResponse> findDetailById(Long id);
}
