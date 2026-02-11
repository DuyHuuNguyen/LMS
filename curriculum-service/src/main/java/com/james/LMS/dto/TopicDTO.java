package com.james.LMS.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@JsonSerialize(keyUsing = ToStringSerializer.class)
@EqualsAndHashCode
public class TopicDTO {
  private Long id;
  private String name;
}
