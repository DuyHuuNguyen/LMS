package com.james.LMS.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TestDTO implements Comparable<TestDTO> {
  private Long id;

  private Integer index;

  private String question;

  private Map<String, Object> chooses = new HashMap<>();

  private String answer;

  @Override
  public int compareTo(TestDTO testDTO) {
    return this.index - testDTO.index;
  }
}
