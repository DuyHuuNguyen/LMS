package com.james.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingDomain {

  @Value("${FE_LOCAL_ORIGIN:NOT_SET}")
  private String feLocalOrigin;

  @Value("${FE_ORIGIN:NOT_SET}")
  private String feOrigin;

  @Value("${BE_LOCAL_ORIGIN:NOT_SET}")
  private String beLocalOrigin;

  @Value("${BE_ORIGIN:NOT_SET}")
  private String beOrigin;

  @EventListener(ApplicationReadyEvent.class)
  public void logDomains() {
    log.info("==================== DOMAIN URLS ====================");
    log.info("FE Local  : {}", feLocalOrigin);
    log.info("FE Online : {}", feOrigin);
    log.info(
        "BE Local  : {}/user-service/swagger-ui/index.html?urls.primaryName=user-service",
        beLocalOrigin);
    log.info(
        "BE Online : {}/user-service/swagger-ui/index.html?urls.primaryName=user-service",
        beOrigin);
    log.info("=====================================================");
  }
}
