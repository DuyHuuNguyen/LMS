package com.james.LMS.repository;

import com.james.LMS.entity.Bucket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
  Optional<Bucket> findByBucketNameAndIsActiveIsTrue(String bucketName);


  @Query("""
      SELECT b
      FROM Bucket b where b.isActive AND b.bucketName like 'video-bucket'
    """
  )
  Optional<Bucket> findByActiveIsTrue();
}
