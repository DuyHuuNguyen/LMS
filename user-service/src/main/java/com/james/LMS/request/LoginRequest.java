package com.james.LMS.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(defaultValue = "23130075@st.hcmuaf.edu.vn")
  private String email;

  @Schema(defaultValue = "23130075@Lms")
  @NotBlank
  private String password;
}
