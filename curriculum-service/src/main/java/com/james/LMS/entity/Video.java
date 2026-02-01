package com.james.LMS.entity;

import jakarta.persistence.*;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "videos")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Video extends BaseSessionContent {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "duration", nullable = false)
  private Time duration;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Column(name = "view")
  private Integer view;

  @Column(name = "size", nullable = false)
  private Integer size;

  @Column(name = "is_preview")
  private Boolean isPreview;

  @Column(name = "video_url", unique = true, nullable = false)
  private String videoUrl;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private Session session;
}
