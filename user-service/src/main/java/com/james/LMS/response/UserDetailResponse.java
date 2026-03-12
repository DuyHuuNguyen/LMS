package com.james.LMS.response;

import java.time.LocalDate;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserDetailResponse {
  private Long id;
  private String username;
  private String email;
  private String avatarUrl;
  private Boolean isInstructor;
  private String instructorName;
  private String instructorAbout;
  private LocalDate createdAt;
  private Long channelId;
}
