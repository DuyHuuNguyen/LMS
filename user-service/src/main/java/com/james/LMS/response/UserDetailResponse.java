package com.james.LMS.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
