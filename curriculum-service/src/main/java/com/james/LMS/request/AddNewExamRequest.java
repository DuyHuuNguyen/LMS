package com.james.LMS.request;

import com.james.LMS.dto.TestDTO;
import java.util.List;

public class AddNewExamRequest {
  private Long curriculumId;
  private Long sessionId;
  private List<TestDTO> testDTOS;
}
