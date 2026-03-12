package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IdentifyTemplate {
  IDENTIFY_CODE_TEMPLATE("%s_%s");

  private final String template;
}
