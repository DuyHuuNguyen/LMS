package com.james.LMS.config;

import java.util.List;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Setter
@ConfigurationProperties(prefix = "cors")
public class AllowedOriginsConfig {
  private List<String> allowedOrigins;

  public String[] toStringArray() {
    return this.allowedOrigins.toArray(new String[0]);
  }
}
