package com.james.LMS.entity;


import com.james.LMS.enums.WatchingContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "lastest_watching_videos")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LastestWatchingVideo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "paused_at")
    private Integer pausedAt;

    @Column(name = "content_type")
    @Enumerated
    private WatchingContentType contentType;
}
