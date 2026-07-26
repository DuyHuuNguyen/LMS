package com.james.LMS.request;

import com.james.LMS.enums.WatchingContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class StoppedWatchingContentRequest {
  private Long curriculumId;
  private Long sessionId;
  private Integer pausedAt;
  private WatchingContentType contentType;
}
