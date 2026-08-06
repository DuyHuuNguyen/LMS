package com.james.LMS.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "curriculum_training_sets")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumTrainingSet extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @Column(name = "training_set_name", nullable = false)
  private String trainingSetName;

  @OneToMany(mappedBy = "curriculumTrainingSet")
  private List<ElementTrainingSet> elements = new ArrayList<>();
}
