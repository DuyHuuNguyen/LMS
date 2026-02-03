package com.james.LMS.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InstructionRequest {
  @NotBlank private String name;
  @NotBlank private String about;
}
