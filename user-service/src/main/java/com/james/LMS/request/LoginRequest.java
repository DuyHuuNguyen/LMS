package com.james.LMS.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {
  @Email(message = "Email invalid")
  @NotBlank
  private String email;

  @NotBlank private String password;
}
