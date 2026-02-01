package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_curriculums")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserCurriculum extends BaseEntity {
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "curriculum_id")
  private Curriculum curriculum;
}
