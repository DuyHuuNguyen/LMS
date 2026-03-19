package com.james.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class WishlistDTO {
  private Long id;
  private Long userId;
  private Long curriculumId;
}
