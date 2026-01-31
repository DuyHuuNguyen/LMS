package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  USER_NOT_FOUND("1000", "User not found exception"),
  PERMISSION_DENIES("1001", "Permission denies exception"),
  ROLE_NOT_FOUND("1002", "Role not found"),
  JWT_INVALID("1003", "Jwt invalid"),
  NOT_MATCHED_PASSWORD("1004", "Password don't match");
  private final String code;
  private final String message;
}
