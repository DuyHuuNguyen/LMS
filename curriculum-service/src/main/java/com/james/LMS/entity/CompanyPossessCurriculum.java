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
@Table(name = "company_possess_curriculums")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyPossessCurriculum extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @ManyToOne
  @JoinColumn(name = "curriculum_id")
  private Curriculum curriculum;
}
