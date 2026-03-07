package com.james.LMS.request;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurriculumCriteria extends BaseCriteria {
  private String keywork;
  private Set<Long> topicId;
  private Long duration;
}
