package com.james.LMS.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "exams")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Exam extends BaseSessionContent {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_id")
  private Session session;

  @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY)
  private List<Test> tests = new ArrayList<>();

  @OneToMany(mappedBy = "exam")
  private List<Note> notes = new ArrayList<>();
}
