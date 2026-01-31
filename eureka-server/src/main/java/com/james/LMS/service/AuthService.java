package com.james.LMS.service;

import com.james.LMS.dto.AuthDTO;

public interface AuthService {
  AuthDTO validToken(String token);
}
