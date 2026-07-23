package com.james.LMS.response;

import com.james.LMS.enums.NoteType;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class NoteResponse implements Comparable<NoteResponse> {
  private Long id;
  private Integer globalIndex;
  private String content;
  private String notedAt;
  private NoteType noteType;
  private Long sessionContentId;

  @Override
  public int compareTo(@NotNull NoteResponse noteResponse) {
    return this.globalIndex - noteResponse.globalIndex;
  }
}
