package com.james.LMS.facade.impl;

import com.james.LMS.enums.UserBehaviorType;
import com.james.LMS.facade.TrackingBehaviorFacade;
import com.james.LMS.message.final_lms_message.StopWatchingSessionContentMessage;
import com.james.LMS.request.StoppedWatchingContentRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.service.FactoryProducerTrackingWatchingContentService;
import com.james.LMS.service.producer.UserPauseVideoProducer;
import com.james.LMS.util.SecurityUserDetailsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingBehaviorFacadeImpl implements TrackingBehaviorFacade {
  private final FactoryProducerTrackingWatchingContentService
      factoryProducerTrackingWatchingContentService;

  @Override
  public BaseResponse<Void> trackingStoppedWatchingContent(StoppedWatchingContentRequest request) {

    StopWatchingSessionContentMessage stopWatchingSessionContentMessage =
        StopWatchingSessionContentMessage.builder()
            .curriculumId(request.getCurriculumId())
            .sessionId(request.getSessionId())
            .userId(SecurityUserDetailsUtil.PRINCIPAL.getId())
            .pausedAt(request.getPausedAt())
            .contentType(request.getContentType())
            .contentId(request.getContentId())
            .messageName("Tracking stopped watch content")
            .build();

    stopWatchingSessionContentMessage.initialBaseInfoMessage();

    UserPauseVideoProducer userPauseVideoProducer =
        (UserPauseVideoProducer)
            this.factoryProducerTrackingWatchingContentService.create(
                UserBehaviorType.USER_PAUSE_VIDEO);

    userPauseVideoProducer.produce(stopWatchingSessionContentMessage);

    return BaseResponse.ok();
  }
}
