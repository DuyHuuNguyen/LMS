package com.james.LMS.entity;

import com.james.LMS.converter.TestConverter;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "tests")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Test extends BaseEntity {
  @Column(name = "index", nullable = false)
  private Integer index;

  @Column(name = "question", nullable = false)
  private String question;

  @ColumnTransformer(write = "?::jsonb")
  @Convert(converter = TestConverter.class)
  @Column(name = "chooses", columnDefinition = "jsonb", nullable = false)
  private Map<String, Object> chooses = new HashMap<>();

  @Column(name = "answer", nullable = false)
  private String answer;

  @ManyToOne
  @JoinColumn(name = "exam_id")
  private Exam exam;
}
