package com.james.LMS.facade;

import com.james.LMS.request.ExamDetailRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ExamDetailResponse;

public interface ExamFacade {
  BaseResponse<ExamDetailResponse> findExamDetail(ExamDetailRequest examDetailRequest);
}
