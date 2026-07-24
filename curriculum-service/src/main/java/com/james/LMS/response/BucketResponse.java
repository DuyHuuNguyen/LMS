package com.james.LMS.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BucketResponse {
  private Long id;
  private String bucketName;
  private Long version;
  private boolean isActive;
  private Long createdAt;
  private Long updatedAt;
}
