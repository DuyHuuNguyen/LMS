package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cart_items")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "cart_id", referencedColumnName = "id")
  private Cart cart;

  @Column(name = "curriculum_id", nullable = false)
  private Long curriculumId;
}
