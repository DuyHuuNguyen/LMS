package com.james.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PurchasedCurriculumDTO {
  private Long id;
  private String title;
  private String headLine;
  private String description;
  private String curriculumThumbnail;

  private String sessionName;
  private Long sessionId;
  private Long videoId;
  private Long examId;
  private Long stoppedAt;
  private String thumbnail;
  private Boolean isFirstTimeLearnCurriculum;
}
