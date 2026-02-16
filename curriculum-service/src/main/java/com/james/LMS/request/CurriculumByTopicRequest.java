package com.james.LMS.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurriculumByTopicRequest extends BaseCriteria {
  @NotNull
  private Long topicId;
}
