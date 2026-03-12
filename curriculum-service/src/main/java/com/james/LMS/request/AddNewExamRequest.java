package com.james.LMS.request;

import com.james.LMS.dto.TestDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AddNewExamRequest {
  private Long curriculumId;
  private Long sessionId;
  private Integer index;
  private String name;
  private Boolean isPreview;
  private List<TestDTO> testDTOS;
}
