package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.facade.TrackingBehaviorFacade;
import com.james.LMS.message.final_lms_message.PauseVideoMessage;
import com.james.LMS.request.StoppedWatchingContentRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.util.SecurityUserDetailsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingBehaviorFacadeImpl implements TrackingBehaviorFacade {

  @Override
  public BaseResponse<Void> trackingStoppedWatchingContent(StoppedWatchingContentRequest request) {

    PauseVideoMessage pauseVideoMessage =
        PauseVideoMessage.builder()
            .curriculumId(request.getCurriculumId())
            .sessionId(request.getSessionId())
            .userId(SecurityUserDetailsUtil.PRINCIPAL.getId())
            .pausedAt(request.getPausedAt())
            .contentType(request.getContentType())
                .messageName("Tracking stopped watch content")
            .build();
    log.info("Received message : {}", pauseVideoMessage.toJsonString());

    return BaseResponse.ok();
  }
}
