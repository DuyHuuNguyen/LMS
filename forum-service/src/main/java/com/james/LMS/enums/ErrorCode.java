package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DEMO("string","string");

    private final String code;
    private final String message;
}
