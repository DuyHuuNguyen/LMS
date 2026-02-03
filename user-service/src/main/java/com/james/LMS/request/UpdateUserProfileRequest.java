package com.james.LMS.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateUserProfileRequest {
  @Hidden private Long id;
  private String username;
  private String instructorName;
  private String instructorAbout;

}
