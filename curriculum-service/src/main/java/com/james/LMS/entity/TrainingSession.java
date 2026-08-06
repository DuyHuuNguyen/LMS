package com.james.LMS.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "training_sessions")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingSession extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "group_id")
  private Group group;

  @Column(name = "started_at")
  private Long startedAt;

  @Column(name = "ended_at")
  private Long endedAt;

  @Column(name = "name", length = 200)
  private String name;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinTable(
      name = "curriculum_training_set_training_sessions",
      joinColumns = @JoinColumn(name = "training_session_id"),
      inverseJoinColumns = @JoinColumn(name = "curriculum_training_set_id"))
  @Builder.Default
  private Set<CurriculumTrainingSet> curriculumTrainingSets = new HashSet<>();

  @OneToMany(mappedBy = "trainingSession")
  @Builder.Default
  private List<TrainingExam> trainingExams = new ArrayList<>();
}
