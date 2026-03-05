package com.james.LMS.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoStreamingPresignRequest {
  @Positive @NotNull private Long curriculumId;
  @Positive @NotNull private Long sessionId;
  @Positive @NotNull private Long videoId;
  private Integer stoppedAt;
}
