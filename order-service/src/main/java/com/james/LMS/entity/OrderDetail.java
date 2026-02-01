package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_details")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @Column(name = "curriculum_id")
  private String curriculumId;
}
