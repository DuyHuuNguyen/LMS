package com.james.LMS.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class InstructorDTO {
  private Long userId;
  private String username;
  private String avatar;
  private String instructorName;
}
