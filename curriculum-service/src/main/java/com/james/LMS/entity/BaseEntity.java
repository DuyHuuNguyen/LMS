package com.james.LMS.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    BaseEntity that = (BaseEntity) o;
    return isActive == that.isActive
        && Objects.equals(id, that.id)
        && Objects.equals(version, that.version)
        && Objects.equals(createdAt, that.createdAt)
        && Objects.equals(updatedAt, that.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, isActive, createdAt, updatedAt);
  }
}
