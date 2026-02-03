package com.james.LMS.entity;

import com.james.LMS.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "roles")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Role extends BaseEntity {

  @Column(name = "role_name", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private RoleEnum roleName;
}
