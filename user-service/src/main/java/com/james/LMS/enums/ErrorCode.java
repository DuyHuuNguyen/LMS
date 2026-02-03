package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  USER_NOT_FOUND("1000", "User not found exception"),
  PERMISSION_DENIES("1001", "Permission denied exception"),
  ROLE_NOT_FOUND("1002", "Role not found"),
  JWT_INVALID("1003", "Jwt invalid"),
  NOT_MATCHED_PASSWORD("1004", "Password doesn't match"),
  USER_ALREADY_EXISTS("1005", "User already exists"),
  SPAM_FORGOT_PASSWORD("1006", "Spam forgot password"),
  OTP_TIMEOUT("1007", "OTP timeout"),
  NOT_MATCHED_OTP("1008", "OPT doesn't match"),
  INSTRUCTOR_ALREADY_EXISTS("1009", "Instructor already exists");

  private final String code;
  private final String message;
}
