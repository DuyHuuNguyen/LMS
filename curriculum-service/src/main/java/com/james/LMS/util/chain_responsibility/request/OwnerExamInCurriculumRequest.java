package com.james.LMS.util.chain_responsibility.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OwnerExamInCurriculumRequest {
  private Long userId;
  private Long curriculumId;
  private Long sessionId;
  private Long examId;
  @Builder.Default private Boolean isInstructor = false;
}
