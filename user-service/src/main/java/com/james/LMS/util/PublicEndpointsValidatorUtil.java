package com.james.LMS.util;

import java.util.List;

public class PublicEndpointsValidatorUtil {
  private static final List<String> SWAGGER_URLS = List.of("/swagger-ui/", "/v3/api-docs");
  private static final List<String> PUBLIC_ENDPOINTS =
      List.of(
          "/api/v1/users/demo",
          "/actuator/health",
          "/actuator/beans",
          "/api/v1/users/login",
          "/api/v1/users/sign-up",
          "/api/v1/users/forgot-password",
          "/api/v1/users/refresh-token",
          "/api/v1/users/verify-otp",
          "/api/v2/users/login");

  public static boolean isSwaggerUrl(String path) {
    return SWAGGER_URLS.stream().anyMatch(path::startsWith);
  }

  public static boolean isPublicEndpoint(String path) {
    return PUBLIC_ENDPOINTS.stream().anyMatch(path::endsWith);
  }
}
