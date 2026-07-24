package com.james.LMS.service;

import com.james.LMS.entity.Bucket;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BucketService {
  Bucket save(Bucket bucket);

  Optional<Bucket> findByName(String bucketName);

  Page<Bucket> findAll(Pageable pageable);

  Optional<Bucket> findByActive();
}
