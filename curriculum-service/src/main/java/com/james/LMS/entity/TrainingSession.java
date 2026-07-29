package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
}
