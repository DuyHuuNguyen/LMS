package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "post_topics")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PostTopic extends BaseEntity{

    @Column(name = "forum_topic_id")
    private Long forumTopicId;

    @ManyToOne
    @JoinColumn(name = "forum_post_id")
    private ForumPost forumPost;
}
