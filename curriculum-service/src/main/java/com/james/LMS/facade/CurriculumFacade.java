package com.james.LMS.facade;

import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.CurriculumReviewResponse;

public interface CurriculumFacade {
  BaseResponse<CurriculumReviewResponse> findCurriculumForReviewById(Long id);
}
