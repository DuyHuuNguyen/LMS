package com.james.LMS.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class InitialUploadLargeFileSessionRequest {
  private String filename;
  private Long fileSizeBytes;
}
