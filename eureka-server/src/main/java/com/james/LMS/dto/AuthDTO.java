package com.james.LMS.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthDTO {
  private Long id;
  private String email;
  private List<String> roles;
}
