package com.james.LMS.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "training_exams")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingExam extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "training_session_id")
  private TrainingSession trainingSession;

  @Column(name = "exam_name")
  private String examName;

  @OneToMany(mappedBy = "trainingExam")
  @Builder.Default
  private List<TrainingEssayTest> trainingEssayTests = new ArrayList<>();

  @OneToMany(mappedBy = "trainingExam")
  @Builder.Default
  private List<TrainingMultipleChoiceTest> trainingMultipleChoiceTests = new ArrayList<>();
}
