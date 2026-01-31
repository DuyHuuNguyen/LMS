package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {
  FORGOT_PASSWORD("FORGOT_PASSWORD_TOKEN_%s"),
  ACCESS_TOKEN("ACCESS_TOKEN_%s"),
  REFRESH_TOKEN("REFRESH_TOKEN_%s");

  private final String cacheKeyTemplate;
}
