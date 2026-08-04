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
@Table(name = "groups")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Group extends BaseEntity {

  @Column(name = "user_admin_group_id")
  private Long userAdminGroupId;

  @Column(name = "max_group_size")
  private Integer maxGroupSize;

  @Column(name = "group_name")
  private String groupName;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @OneToMany(mappedBy = "group")
  @Builder.Default
  private List<GroupMember> groupMembers = new ArrayList<>();
}
