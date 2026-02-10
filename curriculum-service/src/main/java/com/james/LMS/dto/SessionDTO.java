package com.james.LMS.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SessionDTO {
  private Long id;
  private Integer index;
  private String totalTimesStringFormat;
  private Integer totalLectures;
  private List<BaseSessionContentDTO> lectures;
}
