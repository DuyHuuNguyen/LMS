package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_givens")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserGiven extends BaseEntity {
  @Column(name = "giver_id", nullable = false)
  private Long giverId;

  @Column(name = "receiver_id", nullable = false)
  private Long userId;

  @Column(name = "message")
  private String message;

  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @ManyToOne
  @JoinColumn(name = "curriculum_id")
  private Curriculum curriculum;
}
