package com.james.apigateway.config;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
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
  private static final String COOKIE_NAME = "access-token";

  @Value("${auth.public-urls}")
  private String publicUrls;

  private List<String> PUBLIC_APIS;

  @PostConstruct
  public void initPublicUrls() {
    PUBLIC_APIS =
        Arrays.stream(publicUrls.split(",")).map(String::trim).filter(s -> !s.isBlank()).toList();
    log.info("init public urls : {}", PUBLIC_APIS);
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    initPublicUrls();
    String path = exchange.getRequest().getPath().value();

    var isSwagger = SWAGGER_URLS.stream().anyMatch(path::startsWith);
    var isPublic = PUBLIC_APIS.stream().anyMatch(path::startsWith);

    ServerHttpRequest mutatedRequest = this.buildRequestId(exchange);

    if (isSwagger || isPublic)
      return chain.filter(exchange.mutate().request(mutatedRequest).build());

    HttpCookie tokenCookie = exchange.getRequest().getCookies().getFirst(COOKIE_NAME);

    var isMissingToken = (tokenCookie == null || tokenCookie.getValue().isBlank());

    if (isMissingToken) {
      log.info("Request {} is missing token in cookie", path);
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    return chain.filter(exchange.mutate().request(mutatedRequest).build());
  }

  private ServerHttpRequest buildRequestId(ServerWebExchange exchange) {
    String requestId = exchange.getRequest().getHeaders().getFirst("x-request-id");

    if (requestId == null || requestId.isBlank()) {
      requestId = UUID.randomUUID().toString();
    }
    ServerHttpRequest mutatedRequest =
        exchange.getRequest().mutate().header("x-request-id", requestId).build();
    return mutatedRequest;
  }
}
