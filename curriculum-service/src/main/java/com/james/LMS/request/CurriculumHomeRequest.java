package com.james.LMS.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class CurriculumHomeRequest extends BaseCriteria {
  @NotNull(message = "topicSize must not be null")
  @Positive(message = "topicSize must be positive")
  private Integer topicSize;
}
