package com.james.LMS.service.impl;

import com.james.LMS.entity.Bucket;
import com.james.LMS.repository.BucketRepository;
import com.james.LMS.service.BucketService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {
  private final BucketRepository bucketRepository;

  @Override
  public Bucket save(Bucket bucket) {
    return this.bucketRepository.save(bucket);
  }

  @Override
  public Optional<Bucket> findByName(String bucketName) {
    return this.bucketRepository.findByBucketNameAndIsActiveIsTrue(bucketName);
  }

  @Override
  public Page<Bucket> findAll(Pageable pageable) {
    return this.bucketRepository.findAll(pageable);
  }

  @Override
  public Optional<Bucket> findByActive() {
    return this.bucketRepository.findByActiveIsTrue();
  }
}
