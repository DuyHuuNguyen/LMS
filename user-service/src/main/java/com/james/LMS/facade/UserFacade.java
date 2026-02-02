package com.james.LMS.facade;

import com.james.LMS.request.*;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ForgotPasswordResponse;
import com.james.LMS.response.LoginResponse;
import com.james.LMS.response.RefreshTokenResponse;

public interface UserFacade {
  BaseResponse<LoginResponse> login(LoginRequest loginRequest);

  BaseResponse<Void> signUp(UpsertUserRequest upsertUserRequest);

  BaseResponse<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);

  BaseResponse<Void> logout();

  BaseResponse<Void> resetPassword(ResetPasswordRequest resetPasswordRequest);

  BaseResponse<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
}
