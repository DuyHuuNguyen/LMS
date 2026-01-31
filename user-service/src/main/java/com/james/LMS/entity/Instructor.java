package com.james.LMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
