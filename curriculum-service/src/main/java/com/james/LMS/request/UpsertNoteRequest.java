package com.james.LMS.request;

import com.james.LMS.enums.NoteType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UpsertNoteRequest {
  @NotNull @Positive private Long curriculumId;
  @NotNull @Positive private Long sessionId;
  @NotNull @Positive private Long sessionContentId;
  @NotNull private NoteType noteType;
  @Positive @Nullable private Long notedAt;
  @NotNull private String content;
}
