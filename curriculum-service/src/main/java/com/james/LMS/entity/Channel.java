package com.james.LMS.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "channels")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Channel extends BaseEntity {
  @Column(name = "user_id")
  private Long userId;

  @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY)
  private List<Curriculum> curriculums;
}
