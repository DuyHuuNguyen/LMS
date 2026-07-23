package com.james.LMS.dto;

import com.james.LMS.enums.NoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NoteDTO {
  private Long id;
  private Integer globalIndex;
  private String content;
  private Long notedAt;
  private NoteType noteType;
  private Long sessionContentId;
}
