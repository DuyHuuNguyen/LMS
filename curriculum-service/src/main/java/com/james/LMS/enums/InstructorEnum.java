package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InstructorEnum {
  INSTRUCTOR_KEY("LECTURE_KEY_%s");

  private final String content;
}
