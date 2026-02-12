package com.james.LMS.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
// @ToString(callSuper = true)
public class CurriculumHomeRequest extends BaseCriteria {
  @Positive private Integer topicSize;
}
