package com.james.LMS.facade;

import com.james.LMS.request.*;
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

  BaseResponse<PaginationResponse<WishListCurriculumResponse>> findAllWishlist(
      WishlistRequest wishlistRequest);

  BaseResponse<Void> addWishList(Long id);

  BaseResponse<Void> removeWishlist(RemoveWishlistRequest request);

  BaseResponse<SessionDetailResponse> findSessionsOfCurriculum(Long id);
}
