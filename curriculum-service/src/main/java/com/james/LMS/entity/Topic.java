package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "topics")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Topic extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;
}
