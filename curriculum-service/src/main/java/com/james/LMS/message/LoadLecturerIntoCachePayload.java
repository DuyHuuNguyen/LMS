package com.james.LMS.message;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class LoadLecturerIntoCachePayload {
  private Long userId;
}
