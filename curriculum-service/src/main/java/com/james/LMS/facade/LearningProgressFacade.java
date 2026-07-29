package com.james.LMS.facade;

import com.james.LMS.request.CollectLearningTimeRequest;
import com.james.LMS.response.BaseResponse;

public interface LearningProgressFacade {
  BaseResponse<Void> collectLearningTime(CollectLearningTimeRequest request);
}
