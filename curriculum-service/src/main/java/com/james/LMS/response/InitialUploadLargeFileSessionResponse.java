package com.james.LMS.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class InitialUploadLargeFileSessionResponse {
  private Long uploadingSessionId;
  private String s3uploadId;
  private Integer totalParts;
  private Long partSize;
}
