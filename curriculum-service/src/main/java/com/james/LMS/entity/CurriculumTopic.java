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

  @ManyToOne
  @JoinColumn(name = "topic_id")
  private Topic topic;

  @ManyToOne
  @JoinColumn(name = "curriculum_id")
  private Curriculum curriculum;
}
