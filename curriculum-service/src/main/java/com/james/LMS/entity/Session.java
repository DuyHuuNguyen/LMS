package com.james.LMS.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "sessions")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Session extends BaseEntity {

  @Column(name = "index", nullable = false)
  private Integer index;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "curriculum_id")
  private Curriculum curriculum;

  @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
  private Set<Video> videos = new HashSet<>();

  @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
  private Set<Exam> exams = new HashSet<>();
}
