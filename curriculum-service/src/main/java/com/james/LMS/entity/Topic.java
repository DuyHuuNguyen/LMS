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
@Table(name = "topics")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Topic extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;

  @OneToMany(mappedBy = "topic")
  @Builder.Default
  private List<UserTopic> userTopics = new ArrayList<>();

  public void addUserTopic(UserTopic userTopic) {
    this.userTopics.add(userTopic);
  }
}
