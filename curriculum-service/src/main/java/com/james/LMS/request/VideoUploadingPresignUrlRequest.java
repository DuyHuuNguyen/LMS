package com.james.LMS.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VideoUploadingPresignUrlRequest {
  private Long curriculumId;
  private Long sessionId;
}
