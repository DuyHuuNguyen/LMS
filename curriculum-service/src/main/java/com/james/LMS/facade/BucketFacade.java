package com.james.LMS.facade;

import com.james.LMS.request.BucketCriteria;
import com.james.LMS.request.CreateBucketRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.BucketResponse;
import com.james.LMS.response.PaginationResponse;

public interface BucketFacade {
  BaseResponse<BucketResponse> createBucket(CreateBucketRequest request);

  BaseResponse<PaginationResponse<BucketResponse>> findAllBuckets(BucketCriteria criteria);
}
