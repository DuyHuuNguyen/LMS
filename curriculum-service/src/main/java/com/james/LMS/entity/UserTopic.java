package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_topics")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserTopic extends BaseEntity {
  @Column(name = "user_id")
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "topic_id")
  private Topic topic;
}
