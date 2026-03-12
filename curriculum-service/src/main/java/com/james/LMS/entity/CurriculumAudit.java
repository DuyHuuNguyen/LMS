package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "curriculum_audits")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumAudit extends BaseEntity {
  @Column(name = "total_sessions")
  private Integer totaSessions;

  @Column(name = "total_videos")
  private Integer totaVideos;

  @Column(name = "total_exams")
  private Integer totalExams;

  @Column(name = "total_duration_seconds")
  private Long totalDurationSeconds;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "curriculum_id",
      nullable = false,
      unique = true,
      foreignKey = @ForeignKey(name = "fk_curriculum_audits_curriculum"))
  private Curriculum curriculum;
}
