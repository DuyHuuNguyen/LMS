package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ObjectStorageEnum {
  VIDEO("video_%s"),
  THUMBNAIL("thumbnail_%s"),
  VIDEO_HIDDEN("video_%s_hidden"),
  THUMBNAIL_HIDDEN("thumbnail_%s_hidden");

  private final String content;
}
