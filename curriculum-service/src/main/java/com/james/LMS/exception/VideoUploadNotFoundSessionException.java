package com.james.LMS.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VideoUploadNotFoundSessionException extends RuntimeException {
  public VideoUploadNotFoundSessionException(String message) {
    super(message);
  }
}
