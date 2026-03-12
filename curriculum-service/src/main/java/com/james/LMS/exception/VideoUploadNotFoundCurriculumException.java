package com.james.LMS.exception;

import com.james.LMS.enums.ErrorCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VideoUploadNotFoundCurriculumException extends RuntimeException {
  public VideoUploadNotFoundCurriculumException(String message) {
    super(message);
  }

  public VideoUploadNotFoundCurriculumException(ErrorCode errorCode) {
    super(errorCode.getMessage());
  }
}
