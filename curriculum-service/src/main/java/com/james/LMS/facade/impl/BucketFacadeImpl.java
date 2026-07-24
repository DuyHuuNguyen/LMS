package com.james.LMS.facade.impl;

import com.james.LMS.entity.Bucket;
import com.james.LMS.facade.BucketFacade;
import com.james.LMS.request.BucketCriteria;
import com.james.LMS.request.CreateBucketRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.BucketResponse;
import com.james.LMS.response.PaginationResponse;
import com.james.LMS.service.BucketService;
import com.james.LMS.service.MinioService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
@RequiredArgsConstructor
public class BucketFacadeImpl implements BucketFacade {
  private final BucketService bucketService;
  private final MinioService minioService;

  @Override
  @Transactional
  public BaseResponse<BucketResponse> createBucket(CreateBucketRequest request) {
    this.minioService.createBucket(request.getBucketName());
    Bucket bucket =
        this.bucketService
            .findByName(request.getBucketName())
            .orElseGet(() -> this.bucketService.save(Bucket.builder().bucketName(request.getBucketName()).build()));
    return BaseResponse.build(this.toResponse(bucket), true);
  }

  @Override
  public BaseResponse<PaginationResponse<BucketResponse>> findAllBuckets(BucketCriteria criteria) {
    Page<Bucket> bucketPage =
        this.bucketService.findAll(PageRequest.of(criteria.getCurrentPage(), criteria.getPageSize()));
    List<BucketResponse> buckets = bucketPage.getContent().stream().map(this::toResponse).toList();
    return BaseResponse.build(
        PaginationResponse.<BucketResponse>builder()
            .data(buckets)
            .currentPage(criteria.getCurrentPage() + 1)
            .totalElements(Math.toIntExact(bucketPage.getTotalElements()))
            .totalPages(bucketPage.getTotalPages())
            .build(),
        true);
  }

  private BucketResponse toResponse(Bucket bucket) {
    return BucketResponse.builder()
        .id(bucket.getId())
        .bucketName(bucket.getBucketName())
        .version(bucket.getVersion())
        .isActive(bucket.isActive())
        .createdAt(bucket.getCreatedAt())
        .updatedAt(bucket.getUpdatedAt())
        .build();
  }
}
