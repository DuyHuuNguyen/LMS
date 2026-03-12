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
  private String keyword;
  private Set<Long> topicIds;
  private Long duration;
}
