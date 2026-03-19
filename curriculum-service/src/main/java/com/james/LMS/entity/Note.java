package com.james.LMS.entity;

import com.james.LMS.enums.NoteType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "notes")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Note extends BaseEntity implements Comparable<Note> {

  @Column(name = "global_index", nullable = false)
  private Integer globalIndex;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "noted_at", nullable = false)
  private Long notedAt;

  @Column(name = "note_type")
  @Enumerated(EnumType.STRING)
  private NoteType noteType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "video_id")
  private Video video;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exam_id")
  private Exam exam;

  @Override
  public int compareTo(@NotNull Note note) {
    return this.globalIndex - note.globalIndex;
  }
}
