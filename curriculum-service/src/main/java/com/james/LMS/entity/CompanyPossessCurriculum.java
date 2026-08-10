package com.james.LMS.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

  @OneToMany(mappedBy = "companyPossessCurriculum")
  @Builder.Default
  private List<ElementTrainingSet> elementTrainingSets = new ArrayList<>();
}
