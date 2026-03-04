package com.james.LMS.config;

import com.james.LMS.interceptor.AuthenticationTokenProviderInterceptor;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/"), @Server(url = "/curriculum-service")})
@SecurityScheme(
    name = SecurityConfig.SECURITY_REQUIREMENT,
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.COOKIE,
    paramName = AuthenticationTokenProviderInterceptor.AUTHORIZATION)
public class SwaggerConfig {
  @Bean
  public GroupedOpenApi openApi() {
    String[] paths = {"/api/**"};
    return GroupedOpenApi.builder()
        .group("curriculum-service")
        .packagesToScan("com.james.LMS.controller")
        .pathsToMatch(paths)
        .build();
  }
}
