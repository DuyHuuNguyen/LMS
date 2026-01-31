package com.james.LMS.interceptor;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.AuthDTO;
import com.james.LMS.service.AuthService;
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
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationTokenProviderInterceptor extends OncePerRequestFilter {
  private final AuthService authService;

  private static final List<String> SWAGGER_URLS = List.of("/swagger-ui/", "/v3/api-docs");
  private static final List<String> PUBLIC_ENDPOINTS = List.of("/api/v1/internal");
  public static final String AUTHORIZATION = "Authorization";
  private static final int START_OF_TOKEN = 7;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String path = request.getRequestURI();
    log.info("path : {}", path);

    String token = getTokenFromHeader(request);

    if (this.isSwaggerUrl(path) || this.isPublicEndpoint(path)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      log.info("token {}", token);
      AuthDTO authDTO = this.authService.validToken(token);

      List<GrantedAuthority> authorityList =
          authDTO.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

      var principle = SecurityUserDetails.build(authDTO, authorityList);

      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(principle, null, authorityList);

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    } catch (Exception e) {
      log.error("Error validating token: {}", e.getMessage(), e);
      SecurityContextHolder.clearContext();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    filterChain.doFilter(request, response);
  }

  private boolean isSwaggerUrl(String path) {
    return SWAGGER_URLS.stream().anyMatch(path::startsWith);
  }

  public boolean isPublicEndpoint(String path) {
    return PUBLIC_ENDPOINTS.stream().anyMatch(path::endsWith);
  }

  private String getTokenFromHeader(HttpServletRequest request) {
    String headerAuth = request.getHeader(AUTHORIZATION);
    if (headerAuth != null) {
      return headerAuth.substring(START_OF_TOKEN);
    }
    return null;
  }
}
