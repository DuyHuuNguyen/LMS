package com.james.LMS.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class UpdateSessionVideoRequest {
  @Hidden private Long id;
  @Positive
  @NonNull
  private Long newSessionId;
  @Positive
  @NonNull
  private Long curriculumId;

  public void withId(Long id) {
    this.id = id;
  }
}
