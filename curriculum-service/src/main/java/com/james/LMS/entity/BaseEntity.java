package com.james.LMS.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "version", nullable = false)
  @Version
  @Builder.Default
  private Long version = 0L;

  @Column(name = "is_active", nullable = false)
  @Builder.Default
  boolean isActive = true;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  @PrePersist
  protected void prePersist() {
    isActive = true;
    if (this.createdAt == null) createdAt = Instant.now().toEpochMilli();
    if (this.updatedAt == null) updatedAt = Instant.now().toEpochMilli();
  }

  @PreUpdate
  protected void preUpdate() {
    this.updatedAt = Instant.now().toEpochMilli();
  }

  public void softDelete() {
    this.isActive = false;
  }

  public LocalDateTime getLocalDateTimeCreatedAt(String timeZone) {
    return Instant.ofEpochMilli(this.createdAt).atZone(ZoneId.of(timeZone)).toLocalDateTime();
  }
}
