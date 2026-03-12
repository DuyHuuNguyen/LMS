package com.james.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ValidChangeSessionVideoAccessDTO {
  private Long userChanelHolderId;
  private Long curriculumId;
  private Long newSessionId;
  private Long videoId;
}
