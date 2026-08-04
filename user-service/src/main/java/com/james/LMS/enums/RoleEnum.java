package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
  USER("ROLE_USER"),
  INSTRUCTOR("ROLE_INSTRUCTOR"),
  ADMIN("ROLE_SYSTEM_ADMIN"),
  COMPANY_ADMIN("ROLE_COMPANY_ADMIN");
  private final String content;
}
