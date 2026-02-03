package com.james.LMS.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyOTPResponse {
  private String resetPasswordToken;
}
