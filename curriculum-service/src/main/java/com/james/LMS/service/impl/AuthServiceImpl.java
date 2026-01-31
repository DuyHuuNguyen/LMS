package com.james.LMS.service.impl;

import com.james.LMS.dto.AuthDTO;
import com.james.LMS.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    public AuthDTO validToken(String token) {
        return null;
    }
}
