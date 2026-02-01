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
@Table(name = "carts")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseEntity {
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @OneToMany(mappedBy = "cart")
  @Builder.Default
  private List<CartItem> cartItems = new ArrayList<>();
}
