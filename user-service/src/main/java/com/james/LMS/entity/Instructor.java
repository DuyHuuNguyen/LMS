package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "instructors")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Instructor extends BaseEntity {
  @Column(name = "instructor_name", nullable = false)
  private String name;

  @Column(name = "about_instructor")
  private String about;

  @OneToOne(mappedBy = "instructor")
  private User user;

  public void addUser(User user) {
    this.user = user;
  }

  public void changeInstructorName(String instructorName) {
    this.name = instructorName;
  }

  public void changeInstructorAbout(String instructorAbout) {
    this.about = instructorAbout;
  }
}
