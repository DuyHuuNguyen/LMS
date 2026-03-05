package com.james.LMS.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CreateTestDTO implements Serializable {
  private Integer index;

  private String question;

  private Map<String, Object> chooses = new HashMap<>();

  private String answer;
}
