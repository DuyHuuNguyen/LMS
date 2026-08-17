package com.james.LMS.dto;

import com.james.LMS.enums.WatchingContentType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class ActiveCurrentSessionDTO {
  private Long sessionId;
  private Long contentId;
  private WatchingContentType type;
  private Integer pausedAt;
}
