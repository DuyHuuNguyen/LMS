package com.james.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "forum_post_likes")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PostLike extends BaseEntity{
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_like")
    private Boolean isLike;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
