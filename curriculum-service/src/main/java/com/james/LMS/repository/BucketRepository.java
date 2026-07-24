package com.james.LMS.repository;

import com.james.LMS.entity.Bucket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
  Optional<Bucket> findByBucketNameAndIsActiveIsTrue(String bucketName);
}
