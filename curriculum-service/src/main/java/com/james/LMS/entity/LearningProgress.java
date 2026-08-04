package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "learning_progressives")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LearningProgress extends BaseEntity {
  @Column(name = "learning_minutes")
  private Integer learningMinutes;

  @OneToOne
  @JoinColumn(name = "user_curriculum_id")
  private UserCurriculum userCurriculum;

  public boolean isEmptyUserCurriculum() {
    return this.userCurriculum == null;
  }

  public void addUserCurriculum(UserCurriculum userCurriculum) {
    this.userCurriculum = userCurriculum;
  }

  public void addLearningMinutes(Integer minutes) {
    this.learningMinutes += minutes;
  }

  public boolean equal(UserCurriculum userCurriculum) {
    return this.userCurriculum.getId().equals(userCurriculum.getId());
  }
}
