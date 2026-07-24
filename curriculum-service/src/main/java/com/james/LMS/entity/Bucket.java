package com.james.LMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "buckets")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Bucket extends BaseEntity{
    @Column("bucket_name")
    private String bucketName;
}
