package com.james.LMS.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UpdateUserProfileRequest {
  @Hidden private Long id;
  private String username;
  private String instructorName;
  private String instructorAbout;
}
