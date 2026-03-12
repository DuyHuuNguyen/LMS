package com.james.LMS.util.chain_responsibility.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class InstructorCreateCurriculumContentRequest {
  private Long curriculumId;
  private Long userId;
}
