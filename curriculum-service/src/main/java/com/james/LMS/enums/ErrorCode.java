package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  USER_TOPIC_NOT_FOUND("2000", "Not found followed topics of user"), CURRICULUM_NOT_FOUND("2001", "Curriculum not found");

  private final String code;
  private final String message;
}
