package com.james.LMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payment_methods")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;
}
