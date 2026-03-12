package com.james.LMS.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChannelCurriculumResponse {
  private Long id;
  private String title;
  private String headLine;
  private BigDecimal cost;
  private String description;
  private String requirement;
  private String thumbnail;
  private Integer totalSessions;
  private String totalTimesStringFormat;
  private Boolean isWishlisted;
}
