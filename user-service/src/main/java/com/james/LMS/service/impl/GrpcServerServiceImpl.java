package com.james.LMS.service.impl;

import com.example.server.grpc.AccessTokenRequest;
import com.example.server.grpc.AuthResponse;
import com.example.server.grpc.AuthTokenServiceGrpc;
import com.james.LMS.entity.Role;
import com.james.LMS.entity.User;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.RoleEnum;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.InvalidTokenException;
import com.james.LMS.service.JwtService;
import com.james.LMS.service.RoleService;
import com.james.LMS.service.UserService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerServiceImpl extends AuthTokenServiceGrpc.AuthTokenServiceImplBase {
  private final UserService userService;
  private final RoleService roleService;
  private final JwtService jwtService;

  @Override
  public void parseToken(
      AccessTokenRequest request, StreamObserver<AuthResponse> responseObserver) {
    String token = request.getAccessToken();
    try {
      boolean isValidToken = jwtService.validateToken(token);
      if (!isValidToken) throw new InvalidTokenException(ErrorCode.JWT_INVALID);

      String email = jwtService.getEmailFromJwtToken(token);
      User user =
          this.userService
              .findByEmail(email)
              .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
      List<Role> roles = this.roleService.findAllByUserId(user.getId());

      boolean isValidRoles = roles == null;
      if (isValidRoles) throw new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND);
      List<String> roleNames =
          roles.stream().map(Role::getRoleName).map(RoleEnum::getContent).toList();

      AuthResponse authResponse =
          AuthResponse.newBuilder()
              .setUserId(user.getId())
              .addAllRoles(roleNames)
              .setEmail(email)
              .build();
      responseObserver.onNext(authResponse);
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(
          Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
    }
  }
}
