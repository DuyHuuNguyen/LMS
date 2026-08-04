package com.james.LMS.message.final_lms_message;

import com.james.LMS.enums.WatchingContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Getter
public class LearningProgressMessage extends BaseQueueMessage {
  private Long curriculumId;
  private Long contentId;
  private Integer learningMinutes;
  private WatchingContentType type;
  private Long userId;
}
