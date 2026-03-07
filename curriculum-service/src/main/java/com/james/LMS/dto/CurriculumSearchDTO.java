package com.james.LMS.dto;

import java.math.BigDecimal;

public interface CurriculumSearchDTO {
  Long getUserId();

  Long getId();

  String getTitle();

  String getHeadLine();

  BigDecimal getCost();

  String getDescription();

  String getRequirement();

  String getThumbnail();

  Long getTotalDurationSeconds();

  Double getRelevanceScore();
}
