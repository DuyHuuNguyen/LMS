package com.james.apigateway.config;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
public class CorsConfig {

  @Value("${cors.allowed-origins}")
  private String allowedOrigins;

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration config = new CorsConfiguration();

    boolean isAllowedSetOfOrigins = !allowedOrigins.equals("*");

    List<String> allowedOriginList =
        allowedOrigins.equals("*") ? List.of("*") : Arrays.asList(allowedOrigins.split(","));

    config.setAllowedOrigins(allowedOriginList);
    config.setAllowCredentials(isAllowedSetOfOrigins);

    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setMaxAge(3600L);

    this.logAllowedOrigin(allowedOriginList, isAllowedSetOfOrigins);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
  }

  private void logAllowedOrigin(List<String> allowedOrigins, boolean isAllowedSetOfOrigins) {
    log.info("########################-START-CORS-CONFIG-#########################");
    log.info("Origin list: ");
    allowedOrigins.forEach(log::info);
    log.info("with Credentials: {}", isAllowedSetOfOrigins);
    log.info("########################-END-CORS-CONFIG-###########################");
  }
}
