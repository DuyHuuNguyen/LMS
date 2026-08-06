package com.james.LMS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "element_training_in_sets")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ElementTrainingSet extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "curriculum_training_set_id")
  private CurriculumTrainingSet curriculumTrainingSet;

  @ManyToOne
  @JoinColumn(name = "company_possess_curriculum_id")
  private CompanyPossessCurriculum companyPossessCurriculum;
}
