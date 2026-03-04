package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {
  READ_LECTURER_INFO_AND_CACHE,
  CREATE_TESTS_FOR_EXAM,
  CREATE_VIDEO
}
