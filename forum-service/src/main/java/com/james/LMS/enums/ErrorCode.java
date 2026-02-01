package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  FORUM_POST_NOT_FOUND("2000", "Forum post not found"),
  TOPIC_NOT_FOUND("2001","Topic not found"),
  ;

  private final String code;
  private final String message;
}
