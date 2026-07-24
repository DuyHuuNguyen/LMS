package com.james.LMS.message;

import java.io.Serializable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class CreateVideoPayload implements Serializable {
  private String videoUrl;
  private Long curriculumId;
  private Long sessionId;
  private Integer durationSeconds;
  private Integer index;
  private Boolean isPreView;
  private String name;
  private String email;
  private String identifyCode;
}
