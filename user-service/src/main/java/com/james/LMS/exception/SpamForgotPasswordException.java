package com.james.LMS.exception;

import com.james.LMS.enums.ErrorCode;
import lombok.Getter;

@Getter
public class SpamForgotPasswordException extends RuntimeException {
  private String errorCode;
  private String message;

  public SpamForgotPasswordException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode.getCode();
    this.message = errorCode.getMessage();
  }
}
