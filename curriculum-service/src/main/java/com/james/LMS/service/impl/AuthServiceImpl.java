package com.james.LMS.service.impl;

import com.example.server.grpc.AccessTokenRequest;
import com.example.server.grpc.AuthResponse;
import com.example.server.grpc.AuthTokenServiceGrpc;
import com.james.LMS.dto.AuthDTO;
import com.james.LMS.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  @GrpcClient("parse-token")
  private AuthTokenServiceGrpc.AuthTokenServiceBlockingStub authTokenServiceBlockingStub;

  @Override
  public AuthDTO validToken(String token) {
    AccessTokenRequest accessTokenRequest =
        AccessTokenRequest.newBuilder().setAccessToken(token).build();
    AuthResponse authResponse = this.authTokenServiceBlockingStub.parseToken(accessTokenRequest);
    return AuthDTO.builder()
        .id(authResponse.getUserId())
        .email(authResponse.getEmail())
        .roles(authResponse.getRolesList())
        .build();
  }
}
