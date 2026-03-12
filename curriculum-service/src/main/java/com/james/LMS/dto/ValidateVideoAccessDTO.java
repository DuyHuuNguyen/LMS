package com.james.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ValidateVideoAccessDTO {
  private Long userId;
  private Long curriculumId;
  private Long sessionId;
  private Long videoId;
}
