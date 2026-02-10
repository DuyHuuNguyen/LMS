package com.james.LMS.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CurriculumDTO {
  private Long userId;
  private String username;
  private String title;
  private String headLine;
  private BigDecimal cost;
  private String description;
  private String name;
  private String thumbnail;
  private String topicName;
}
