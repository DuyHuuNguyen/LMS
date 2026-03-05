package com.james.LMS.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class VideoUploadingPresignUrlRequest {
  @Positive
  @NotNull
  private Long curriculumId;
  @Positive
  @NotNull
  private Long sessionId;
  @NotNull
  private String videoName;
  @NotNull
  private Boolean isPreView;
  @Positive
  @NotNull
  private Integer index;
  @Positive
  @NotNull
  private Long durationSeconds;
}
