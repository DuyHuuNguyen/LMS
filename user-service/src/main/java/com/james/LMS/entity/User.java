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
@Table(name = "users")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "avatar_key")
  private String avatarKey;

  @Column(name = "date_of_birth", nullable = false)
  private Long dateOfBirth;

  @Column(name = "is_loyal_customer", nullable = false)
  private Boolean isLoyalCustomer;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Builder.Default
  private List<Role> roles = new ArrayList<>();

  public void changePassword(String newPasswordEncoded) {
    this.password = newPasswordEncoded;
  }
}
