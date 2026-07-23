package com.james.LMS.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class TrackingUploadVideoRequest {
  private Integer part;
  private Long sessionId;
}
