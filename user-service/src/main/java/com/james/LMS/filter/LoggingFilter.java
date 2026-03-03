package com.james.LMS.filter;

import com.james.LMS.config.SecurityUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      long start = System.currentTimeMillis();
      String requestId = request.getHeader("x-request-id");
      MDC.put("requestId", requestId);
      response.setHeader("x-request-id", requestId);

      SecurityUserDetails principal =
          (SecurityUserDetails)
              SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String email = principal.getUsername() != null ? principal.getUsername() : "anonymous";
      MDC.put("email", email);

      log.info(
          "Incoming Request: method={}, path={}", request.getMethod(), request.getRequestURI());

      filterChain.doFilter(request, response);

      long duration = System.currentTimeMillis() - start;

      log.info("Outgoing Response: status={}, duration={}ms", response.getStatus(), duration);
    } finally {
      MDC.clear();
    }
  }
}
