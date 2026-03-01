package com.james.LMS.facade;

import com.james.LMS.request.CurriculumByTopicRequest;
import com.james.LMS.request.CurriculumHomeRequest;
import com.james.LMS.request.PurchasedCurriculumCriteria;
import com.james.LMS.request.TopicCriteria;
import com.james.LMS.response.*;

public interface CurriculumFacade {
  BaseResponse<CurriculumReviewResponse> findCurriculumForReviewById(Long id);

  BaseResponse<CurriculumHomeResponse> findCurriculumForHome(
      CurriculumHomeRequest curriculumHomeRequest);

  BaseResponse<PaginationResponse<TopicResponse>> findAllTopicByCriteria(
      TopicCriteria topicCriteria);

  BaseResponse<CurriculumHomeResponse> findCurriculumForHomeNewFlow(
      CurriculumHomeRequest curriculumHomeRequest);

  BaseResponse<PaginationResponse<CurriculumResponse>> findCurriculumByTopicId(
      CurriculumByTopicRequest curriculumByTopicRequest);

  BaseResponse<PaginationResponse<PurchasedCurriculumResponse>> findAllPurchasedCurriculums(
      PurchasedCurriculumCriteria purchasedCurriculumCriteria);
}
