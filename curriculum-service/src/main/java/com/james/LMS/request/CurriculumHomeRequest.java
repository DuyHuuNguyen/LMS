package com.james.LMS.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
// @ToString(callSuper = true)
public class CurriculumHomeRequest extends BaseCriteria {
  private Integer topicSize;
}
