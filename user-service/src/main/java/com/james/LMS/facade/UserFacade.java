package com.james.LMS.facade;

import com.james.LMS.request.LoginRequest;
import com.james.LMS.request.RefreshTokenRequest;
import com.james.LMS.request.UpsertUserRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.LoginResponse;
import com.james.LMS.response.RefreshTokenResponse;

public interface UserFacade {
  BaseResponse<LoginResponse> login(LoginRequest loginRequest);

  BaseResponse<Void> signUp(UpsertUserRequest upsertUserRequest);

  BaseResponse<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
