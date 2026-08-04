package com.james.LMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "companies")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Company extends BaseEntity {
  @Column(name = "company_name")
  private String companyName;

  @Column(name = "user_admin_company_id")
  private Long userAdminCompanyId;

  @OneToMany(mappedBy = "company")
  @Builder.Default
  private List<CompanyPossessCurriculum> companyPossessCurriculums = new ArrayList<>();

  @OneToMany(mappedBy = "company")
  @Builder.Default
  private List<Group> groups = new ArrayList<>();

  public void changeCompanyName(String companyName) {
    this.companyName = companyName;
  }
}
