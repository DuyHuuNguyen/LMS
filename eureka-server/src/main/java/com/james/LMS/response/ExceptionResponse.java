package com.james.LMS.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final String errorCode;
    private final String message;
}
