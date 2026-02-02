package com.james.LMS.facade;

import com.james.LMS.request.LoginRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.LoginResponse;

public interface UserFacade {
  BaseResponse<LoginResponse> login(LoginRequest loginRequest);
}
