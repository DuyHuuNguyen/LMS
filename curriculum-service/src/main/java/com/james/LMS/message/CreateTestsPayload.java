package com.james.LMS.message;

import com.james.LMS.dto.CreateTestDTO;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CreateTestsPayload implements Serializable {
  private Long examId;
  private List<CreateTestDTO> createTestDTOS;
}
