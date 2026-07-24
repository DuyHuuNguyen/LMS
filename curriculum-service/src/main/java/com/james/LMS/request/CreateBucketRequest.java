package com.james.LMS.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBucketRequest {
  @NotBlank
  @Size(min = 3, max = 63)
  @Pattern(regexp = "[a-z0-9][a-z0-9.-]*[a-z0-9]", message = "must be a valid MinIO bucket name")
  private String bucketName;
}
