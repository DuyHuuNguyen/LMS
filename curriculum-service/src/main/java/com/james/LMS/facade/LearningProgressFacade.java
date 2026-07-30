package com.james.LMS.facade;

import com.james.LMS.request.CollectLearningTimeRequest;
import com.james.LMS.request.LearningProgressRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.LearningProgressResponse;
import com.james.LMS.response.SlicePaginationResponse;

public interface LearningProgressFacade {
  BaseResponse<Void> collectLearningTime(CollectLearningTimeRequest request);

  BaseResponse<SlicePaginationResponse<LearningProgressResponse>> findLearningProgress(
      LearningProgressRequest request);
}
