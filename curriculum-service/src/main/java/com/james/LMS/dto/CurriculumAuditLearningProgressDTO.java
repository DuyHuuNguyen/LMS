package com.james.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CurriculumAuditLearningProgressDTO {
  private Long curriculumId;
  private Integer learningMinutes;
  private String title;
  private Long totalDurationSeconds;
  private String thumbnail;
}
