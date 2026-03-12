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
public class WishListCurriculumResponse {
  private Long id;
  private Long wishlistId;
  private String title;
  private String headLine;
  private BigDecimal cost;
  private String description;
  private String requirement;
  private String thumbnail;
  private Long topicId;
  private String topicName;
}
