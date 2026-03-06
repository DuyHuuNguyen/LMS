package com.james.LMS.dto;

import java.math.BigDecimal;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
public class CurriculumChannelDTO {
  private Long id;
  private String title;
  private String headLine;
  private BigDecimal cost;
  private String description;
  private String requirement;
  private String thumbnail;
  private Integer totalSessions;
  private Long totalDuration;
}
