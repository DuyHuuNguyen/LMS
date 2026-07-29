package com.james.LMS.facade.impl;

import com.james.LMS.enums.UserBehaviorType;
import com.james.LMS.facade.LearningProgressFacade;
import com.james.LMS.message.final_lms_message.LearningProgressMessage;
import com.james.LMS.request.CollectLearningTimeRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.service.FactoryProducerTrackingWatchingContentService;
import com.james.LMS.service.producer.UserBehaviorProducer;
import com.james.LMS.util.SecurityUserDetailsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearningProgressFacadeImpl implements LearningProgressFacade {
  private final FactoryProducerTrackingWatchingContentService
      factoryProducerTrackingWatchingContentService;

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
}
