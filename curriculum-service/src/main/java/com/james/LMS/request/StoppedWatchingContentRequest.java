package com.james.LMS.request;

import com.james.LMS.enums.WatchingContentType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class StoppedWatchingContentRequest {

  @NotNull
  @Positive
  private Long curriculumId;

  @NotNull
  @Positive
  private Long sessionId;

  @NotNull
  @PositiveOrZero
  private Integer pausedAt;

  @NotNull
  @Positive
  private Long contentId;

  @NotNull
  private WatchingContentType contentType;
}