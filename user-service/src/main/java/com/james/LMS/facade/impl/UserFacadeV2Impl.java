package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.enums.TokenType;
import com.james.LMS.facade.UserFacadeV2;
import com.james.LMS.request.LoginRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.service.CacheService;
import com.james.LMS.service.JwtService;
import com.james.LMS.service.RoleService;
import com.james.LMS.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacadeV2Impl implements UserFacadeV2 {
  private final UserService userService;
  private final JwtService jwtService;
  private final CacheService cacheService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;

  private static final int COOKIE_ACCESS_TOKEN_TTL = 60 * 60;
  private static final int COOKIE_REFRESH_TOKEN_TTL = 60 * 60 * 14;

  @Override
  public BaseResponse<Void> login(LoginRequest loginRequest, HttpServletResponse response) {
    log.info("Login v2");
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

    cacheService.store(refreshTokenCacheKey, refreshToken, 14, TimeUnit.DAYS);

    ResponseCookie accessTokenCookie =
        ResponseCookie.from(SecurityConfig.COOKIE_SECURITY_NAME, accessToken)
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .path("/")
            .maxAge(COOKIE_ACCESS_TOKEN_TTL)
            .build();

    ResponseCookie refreshTokenCookie =
        ResponseCookie.from(SecurityConfig.COOKIE_REFRESH_TOKEN_NAME, accessToken)
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .path("/")
            .maxAge(COOKIE_REFRESH_TOKEN_TTL)
            .build();

    ResponseCookie validateLoginCookie =
        ResponseCookie.from(SecurityConfig.VALIDATE_LOGIN, "true")
            .httpOnly(false)
            .secure(false) // localhost
            .sameSite("Lax")
            .path("/")
            .maxAge(COOKIE_REFRESH_TOKEN_TTL)
            .build();

    response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, validateLoginCookie.toString());
    return BaseResponse.ok();
  }
}
