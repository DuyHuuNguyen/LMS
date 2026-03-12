package com.james.LMS.response;

import com.james.LMS.dto.TestDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ExamDetailResponse {
  private Long id;
  private Integer index;
  private String name;
  private Boolean isPreview;

  private List<TestDTO> testDTOS;
}
