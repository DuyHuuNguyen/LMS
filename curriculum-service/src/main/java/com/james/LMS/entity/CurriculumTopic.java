package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "curriculum_topics")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumTopic extends BaseEntity {
  @Column(name = "topic_id", nullable = false)
  private Long topicId;

  @ManyToOne
  @JoinColumn(name = "curriculum_id")
  private Curriculum curriculum;
}
