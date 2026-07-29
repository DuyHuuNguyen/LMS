package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "essay_test_answers")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EssayTestAnswer extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "group_member_id")
  private GroupMember groupMember;

  @Column(name = "answer")
  private String answer;

  @ManyToOne
  @JoinColumn(name = "training_essay_test_id")
  private TrainingEssayTest trainingEssayTest;
}
