package com.james.LMS.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpsertUserRequest {
  @Hidden private Long id;

  @NotBlank private String username;

  @Email(message = "Email invalid")
  @NotBlank
  private String email;

  @NotBlank private String password;
}
