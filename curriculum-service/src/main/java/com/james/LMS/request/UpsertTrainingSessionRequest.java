package com.james.LMS.request;

import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDateTime;
import lombok.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpsertTrainingSessionRequest {
  @Hidden private Long groupId;

  private LocalDateTime startedAt;
  private LocalDateTime endedAt;
  private Boolean isStartedRightNow;
  private String trainingSessionName;

  public void withGroupId(Long id) {
    this.groupId = id;
  }
}
