package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "training_essay_tests")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEssayTest extends BaseEntity {

  @Column(name = "index", nullable = false)
  private Integer index;

  @Column(name = "question", nullable = false)
  private String question;

  @ManyToOne
  @JoinColumn(name = "training_exam_id")
  private TrainingExam trainingExam;
}
