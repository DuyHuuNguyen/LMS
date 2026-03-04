package com.james.LMS.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateVideoPayload {
  private String videoUrl;
  private Long curriculumId;
  private Long sessionId;
}
