package com.james.LMS.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "post_paragraphs")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PostParagraph extends BaseEntity implements Comparable<PostParagraph> {

    @Column(name ="index",nullable = false)
    private Integer index;

    @Column(name = "header")
    private String header;

    @Column(name = "content")
    private  String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_description")
    private String imageDescription;

    @ManyToOne
    @JoinColumn(name = "forum_post_id")
    private Post forumPost;


    @Override
    public int compareTo(PostParagraph postParagraph) {
        return this.index - postParagraph.index;
    }
}
