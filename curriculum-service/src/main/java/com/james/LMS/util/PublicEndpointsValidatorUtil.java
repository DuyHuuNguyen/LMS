package com.james.LMS.util;

import java.util.List;

public class PublicEndpointsValidatorUtil {
  private static final List<String> SWAGGER_URLS = List.of("/swagger-ui/", "/v3/api-docs");
  private static final List<String> PUBLIC_ENDPOINTS =
      List.of("/api/v1/internal", "/actuator/health", "/actuator/beans");

  public static boolean isSwaggerUrl(String path) {
    return SWAGGER_URLS.stream().anyMatch(path::startsWith);
  }

  public static boolean isPublicEndpoint(String path) {
    return PUBLIC_ENDPOINTS.stream().anyMatch(path::endsWith);
  }
}
