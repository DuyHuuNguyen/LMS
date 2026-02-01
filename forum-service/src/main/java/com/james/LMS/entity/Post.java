package com.james.LMS.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "forum_posts")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity{

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @OneToOne
    @JoinColumn(name = "parent_post_id",referencedColumnName = "id")
    private Post parentPost;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category forumCategory;


    @OneToMany(mappedBy = "forumPost")
    @Builder.Default
    private List<PostParagraph> postParagraphs = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<PostLike> postLikes = new ArrayList<>();
}
