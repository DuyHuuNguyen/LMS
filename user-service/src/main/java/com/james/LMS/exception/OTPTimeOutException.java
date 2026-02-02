package com.james.LMS.exception;

import com.james.LMS.enums.ErrorCode;
import lombok.Getter;

@Getter
public class OTPTimeOutException extends RuntimeException {
    private String errorCode;
    private String message;
    public OTPTimeOutException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode =errorCode.getCode();
        this.message =errorCode.getMessage();

    }
}
