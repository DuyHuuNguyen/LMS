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

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "instructor_id", referencedColumnName = "id")
  private Instructor instructor;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Builder.Default
  private List<Role> roles = new ArrayList<>();

  public void changePassword(String newPasswordEncoded) {
    this.password = newPasswordEncoded;
  }

  public void addRole(Role role) {
    this.roles.add(role);
  }

  public void addInstructor(Instructor instructor) {
    this.instructor = instructor;
    this.instructor.addUser(this);
  }

  public void addAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }
}
