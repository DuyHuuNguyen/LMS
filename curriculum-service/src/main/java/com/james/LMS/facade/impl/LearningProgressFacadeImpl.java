package com.james.LMS.facade.impl;

import com.james.LMS.dto.CurriculumAuditLearningProgressDTO;
import com.james.LMS.enums.UserBehaviorType;
import com.james.LMS.facade.LearningProgressFacade;
import com.james.LMS.message.final_lms_message.LearningProgressMessage;
import com.james.LMS.request.CollectLearningTimeRequest;
import com.james.LMS.request.LearningProgressRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.LearningProgressResponse;
import com.james.LMS.response.SlicePaginationResponse;
import com.james.LMS.service.FactoryProducerTrackingWatchingContentService;
import com.james.LMS.service.LearningProgressService;
import com.james.LMS.service.producer.UserBehaviorProducer;
import com.james.LMS.util.SecurityUserDetailsUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearningProgressFacadeImpl implements LearningProgressFacade {
  private final FactoryProducerTrackingWatchingContentService
      factoryProducerTrackingWatchingContentService;
  private final LearningProgressService learningProgressService;

  @Value("${learing-progress.watch-precents}")
  private Integer computingLearningPercentThreshHold;

  private int pageSizeDefault = 10;

  @Override
  public BaseResponse<Void> collectLearningTime(CollectLearningTimeRequest request) {

    UserBehaviorProducer userLearningProgressProducer =
        this.factoryProducerTrackingWatchingContentService.create(
            UserBehaviorType.USER_LEARNING_PROGRESS);

    LearningProgressMessage learningProgressMessage =
        LearningProgressMessage.builder()
            .contentId(request.getContentId())
            .learningMinutes(request.getLearningMinutes())
            .userId(SecurityUserDetailsUtil.PRINCIPAL.getId())
            .curriculumId(request.getCurriculumId())
            .type(request.getType())
            .messageName("Collect user's learning time data to compute the progressive bar")
            .build();

    learningProgressMessage.initialBaseInfoMessage();

    userLearningProgressProducer.produce(learningProgressMessage);

    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<SlicePaginationResponse<LearningProgressResponse>> findLearningProgress(
      LearningProgressRequest request) {

    Slice<CurriculumAuditLearningProgressDTO> learningProgressSlice =
        this.learningProgressService.findAllByUserId(
            SecurityUserDetailsUtil.PRINCIPAL.getId(),
            PageRequest.of(request.computeCurrentPage(), pageSizeDefault));

    List<LearningProgressResponse> learningProgressResponses =
        learningProgressSlice
            .get()
            .map(
                dto -> {
                  LearningProgressResponse learningProgressResponse =
                      LearningProgressResponse.builder()
                          .curriculumId(dto.getCurriculumId())
                          .title(dto.getTitle())
                          .thumbnail(dto.getThumbnail())
                          .build();

                  learningProgressResponse.computePercents(
                      dto.getLearningMinutes(), dto.getTotalDurationSeconds());
                  learningProgressResponse.computeCompleteCurriculum(
                      this.computingLearningPercentThreshHold);

                  return learningProgressResponse;
                })
            .toList();

    return BaseResponse.build(
        SlicePaginationResponse.<LearningProgressResponse>builder()
            .data(learningProgressResponses)
            .currentPage(request.getCurrentPage())
            .build(),
        true);
  }
}
