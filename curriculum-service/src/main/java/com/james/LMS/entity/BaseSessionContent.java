package com.james.LMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseSessionContent extends BaseEntity {
  @Column(name = "index", nullable = false)
  private Integer index;

  @Column(name = "name")
  private String name;

  @Column(name = "is_preview")
  private Boolean isPreview;
}
