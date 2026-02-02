package com.james.LMS.facade.impl;

import com.james.LMS.entity.Role;
import com.james.LMS.entity.User;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.RoleEnum;
import com.james.LMS.enums.TokenType;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.exception.UserAlreadyExistException;
import com.james.LMS.facade.UserFacade;
import com.james.LMS.request.LoginRequest;
import com.james.LMS.request.RefreshTokenRequest;
import com.james.LMS.request.UpsertUserRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.LoginResponse;
import com.james.LMS.response.RefreshTokenResponse;
import com.james.LMS.service.CacheService;
import com.james.LMS.service.JwtService;
import com.james.LMS.service.RoleService;
import com.james.LMS.service.UserService;
import jakarta.transaction.Transactional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;
  private final JwtService jwtService;
  private final CacheService cacheService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;

  @Override
  public BaseResponse<LoginResponse> login(LoginRequest loginRequest) {
    var authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    var accessToken = jwtService.generateAccessToken(loginRequest.getEmail());
    var refreshToken = jwtService.generateRefreshToken(loginRequest.getEmail());

    var accessTokenCacheKey =
        String.format(TokenType.ACCESS_TOKEN.getCacheKeyTemplate(), loginRequest.getEmail());
    var refreshTokenCacheKey =
        String.format(TokenType.REFRESH_TOKEN.getCacheKeyTemplate(), loginRequest.getEmail());

    cacheService.store(accessTokenCacheKey, accessToken, 1, TimeUnit.HOURS);
    cacheService.store(refreshTokenCacheKey, refreshToken, 14, TimeUnit.DAYS);

    return BaseResponse.build(
        LoginResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build(), true);
  }

  @Override
  @Transactional
  public BaseResponse<Void> signUp(UpsertUserRequest upsertUserRequest) {

    boolean isExistUser = this.userService.existsUserByEmail(upsertUserRequest.getEmail());
    if (isExistUser) throw new UserAlreadyExistException(ErrorCode.USER_ALREADY_EXISTS);

    String passwordEncoded = this.passwordEncoder.encode(upsertUserRequest.getPassword());
    User user =
        User.builder()
            .username(upsertUserRequest.getUsername())
            .email(upsertUserRequest.getEmail())
            .password(passwordEncoded)
            .build();

    Role userRole =
        this.roleService
            .findByRoleName(RoleEnum.USER)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND));
    user.addRole(userRole);

    this.userService.save(user);

    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {

    String email = this.jwtService.getEmailFromJwtToken(refreshTokenRequest.getRefreshToken());
    String refreshTokenKey = String.format(TokenType.REFRESH_TOKEN.getCacheKeyTemplate(), email);

    boolean isInvalidRefreshToken = !this.cacheService.hasKey(refreshTokenKey);
    if (isInvalidRefreshToken) throw new PermissionDeniedException(ErrorCode.JWT_INVALID);

    String accessToken = this.jwtService.generateAccessToken(email);

    RefreshTokenResponse refreshTokenResponse =
        RefreshTokenResponse.builder().accessToken(accessToken).build();
    return BaseResponse.build(refreshTokenResponse, true);
  }
}
