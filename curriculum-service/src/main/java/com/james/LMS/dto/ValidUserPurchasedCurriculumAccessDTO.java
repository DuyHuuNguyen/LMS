package com.james.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ValidUserPurchasedCurriculumAccessDTO {
  private Long userId;
  private Long curriculumId;
}
