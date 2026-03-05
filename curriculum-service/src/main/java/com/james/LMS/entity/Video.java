package com.james.LMS.entity;

import jakarta.persistence.*;
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

  @Column(name = "duration_seconds", nullable = false)
  private Long durationSeconds;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Column(name = "identify_code", nullable = false, unique = true)
  private String identifyCode;

  @Column(name = "view")
  private Integer view;

  @Column(name = "size", nullable = false)
  private Integer size;

  @Column(name = "video_url", unique = true, nullable = false)
  private String videoUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_id")
  private Session session;

  public void addSession(Session session) {
    this.session = session;
  }
}
