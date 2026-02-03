package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResetPasswordKey {
  OTP_KEY("RESET_PASSWORD_%s"),
  TIMEOUT_RETRY_KEY("TIMEOUT_RETRY_%s");

  private final String content;
}
