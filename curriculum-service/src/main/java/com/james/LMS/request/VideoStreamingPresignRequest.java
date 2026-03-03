package com.james.LMS.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoStreamingPresignRequest {
  private Long curriculumId;
  private Long sessionId;
  private Long videoId;
  private Integer stoppedAt;
}
