package com.james.apigateway.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {
  private static final List<String> SWAGGER_URLS = List.of("/swagger-ui/", "/v3/api-docs");
  private static final List<String> PUBLIC_APIS =
      List.of(
          "/api/v1/auth/login",
          "/api/v1/auth/authorization",
          "/api/v1/auth/forgot-password",
          "/api/v1/auth/verify-otp",
          "/api/v1/users/sign-up");

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    ServerHttpRequest request = exchange.getRequest();
    HttpHeaders headers = request.getHeaders();

    String path = String.valueOf(exchange.getRequest().getPath());
    log.info("Path of request :{}", path);

    var isSwagger = SWAGGER_URLS.stream().anyMatch(path::startsWith);
    var isPublic = PUBLIC_APIS.stream().anyMatch(path::startsWith);

    log.info("Endpoint is swagger :{}", isSwagger);
    log.info("Endpoint id public :{}", isPublic);

    if (isSwagger || isPublic) return chain.filter(exchange);

    var authenticationHeaders = headers.get(HttpHeaders.AUTHORIZATION);
    var isNullAuthentication =
        authenticationHeaders == null || authenticationHeaders.getFirst() == null;

    var isMissingAcceptToken =
        isNullAuthentication || !authenticationHeaders.getFirst().startsWith("Bearer ");
    if (isMissingAcceptToken) {
      log.info("Request {} is missing token in header", path);
      return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
          .then(exchange.getResponse().setComplete());
    }
    return chain.filter(exchange);
  }
}
