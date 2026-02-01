package com.james.LMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "forum_categories")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name ="description")
    private String description;

}
