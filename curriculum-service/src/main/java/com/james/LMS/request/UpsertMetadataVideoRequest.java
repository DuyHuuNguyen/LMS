package com.james.LMS.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UpsertMetadataVideoRequest {
  @Hidden private Long id;
  private Long durationSeconds;
  private String thumbnail;
  private Integer size;

  public void withId(Long id) {
    this.id = id;
  }
}
