package com.james.LMS.message.final_lms_message;

import com.james.LMS.enums.WatchingContentType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PauseVideoMessage extends BaseQueueMessage {
  private Long curriculumId;
  private Long sessionId;
  private Long userId;
  private Integer pausedAt;
  private WatchingContentType contentType;
}
