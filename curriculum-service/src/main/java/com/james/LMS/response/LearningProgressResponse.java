package com.james.LMS.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class LearningProgressResponse {
  private Long curriculumId;
  private String title;
  private String thumbnail;
  private Double percents;
  private Boolean isCompleted;

  public void computePercents(Integer learningMinutes, Long totalDurationSeconds) {
    if (totalDurationSeconds == null || totalDurationSeconds == 0) {
      this.percents = 0d;
      return;
    }

    this.percents = (learningMinutes * 60d / totalDurationSeconds) * 100;
  }

  public void computeCompleteCurriculum(Integer computingLearningPercentThreshHold) {
    this.isCompleted = this.percents >= computingLearningPercentThreshHold;
  }
}
