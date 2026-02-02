package com.james.LMS.exception;

import com.james.LMS.enums.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAlreadyExistException extends RuntimeException {
    private final String ErrorCode;
    private final String message;

    public UserAlreadyExistException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.ErrorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
