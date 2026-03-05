package com.james.LMS.message;

import java.io.Serializable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class LoadLecturerIntoCachePayload implements Serializable {
  private Long userId;
}
