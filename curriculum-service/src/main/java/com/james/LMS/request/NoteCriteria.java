package com.james.LMS.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class NoteCriteria extends BaseCriteria {
  private Long curriculumId;
}
