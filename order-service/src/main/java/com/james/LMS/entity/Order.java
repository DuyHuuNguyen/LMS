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
@Table(name = "orders")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @OneToMany(mappedBy = "order")
  @Builder.Default
  private List<OrderDetail> orderDetails = new ArrayList<>();
}
