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
@Table(name = "group_members")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember extends BaseEntity {
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "group_id")
  private Group group;

  @OneToMany(mappedBy = "groupMember")
  @Builder.Default
  private List<EssayTestAnswer> essayTestAnswers = new ArrayList<>();

  @OneToMany(mappedBy = "groupMember")
  @Builder.Default
  private List<MultipleChoiceTestAnswer> multipleChoiceTestAnswers = new ArrayList<>();
}
