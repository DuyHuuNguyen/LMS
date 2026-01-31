package com.james.LMS.interceptor;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.TokenType;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.InvalidTokenException;
import com.james.LMS.service.CacheService;
import com.james.LMS.service.JwtService;
import com.james.LMS.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthTokenProviderInterceptor extends OncePerRequestFilter {

  private static final String RESET_PASSWORD_URL = "";
  private final UserService userService;
  private final CacheService cacheService;
  private final JwtService jwtService;

  public static final String AUTHORIZATION = "Authorization";
  private static final int START_OF_TOKEN = 7;
  private static final List<String> SWAGGER_URLS = List.of("/swagger-ui/", "/v3/api-docs");
  private static final List<String> PUBLIC_ENDPOINTS = List.of("/");

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String path = request.getRequestURI();
    log.info("Path of request: {}", path);

    if (this.isSwaggerUrl(path) || this.isPublicEndpoint(path)) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = this.getJwtTokenFromHeader(request);

    try {
      boolean isValidToken = jwtService.validateToken(token);
      if (!isValidToken) throw new InvalidTokenException(ErrorCode.JWT_INVALID);

      String email = jwtService.getEmailFromJwtToken(token);
      if (!validateTokenFromCache(email, token, path))
        throw new InvalidTokenException(ErrorCode.JWT_INVALID);

      SecurityUserDetails securityUserDetails = this.buildUserDetailsByEmail(email);

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
              securityUserDetails, null, securityUserDetails.getAuthorities());

      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);

    } catch (InvalidTokenException invalidTokenException) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  private SecurityUserDetails buildUserDetailsByEmail(String email) {
    var user =
        userService
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    List<GrantedAuthority> authorityList =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName().getContent()))
            .collect(Collectors.toList());

    return SecurityUserDetails.build(user, authorityList);
  }

  private Boolean validateTokenFromCache(String email, String token, String path) {
    if (token == null) return false;

    if (path.startsWith(RESET_PASSWORD_URL)) return true;

    var accessTokenCacheKey = String.format(TokenType.ACCESS_TOKEN.getCacheKeyTemplate(), email);

    return cacheService.hasKey(accessTokenCacheKey)
        && cacheService.retrieve(accessTokenCacheKey).equals(token);
  }

  private boolean isSwaggerUrl(String path) {
    return SWAGGER_URLS.stream().anyMatch(path::startsWith);
  }

  public boolean isPublicEndpoint(String path) {
    return PUBLIC_ENDPOINTS.stream().anyMatch(path::endsWith);
  }

  public String getJwtTokenFromHeader(HttpServletRequest request) {
    String headerAuth = request.getHeader(AUTHORIZATION);
    if (headerAuth != null) {
      return headerAuth.substring(START_OF_TOKEN);
    }
    return null;
  }
}
