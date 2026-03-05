package com.james.LMS.exception;

import com.james.LMS.enums.ErrorCode;
import lombok.Getter;

@Getter
public class UploadFileException extends RuntimeException {
    private final String errorCode;
    private final String message;

    public UploadFileException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}