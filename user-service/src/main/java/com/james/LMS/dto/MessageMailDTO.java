package com.james.LMS.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class MessageMailDTO {
  private String to;
  private String from;
  private String subject;
  private String content;
}
