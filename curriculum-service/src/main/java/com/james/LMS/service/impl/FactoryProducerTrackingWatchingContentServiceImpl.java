package com.james.LMS.service.impl;

import com.james.LMS.enums.UserBehaviorType;
import com.james.LMS.service.FactoryProducerTrackingWatchingContentService;
import com.james.LMS.service.producer.*;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactoryProducerTrackingWatchingContentServiceImpl
    implements FactoryProducerTrackingWatchingContentService {

  private final UserClickCourseProducer userClickCourseProducer;
  private final UserViewTrialVideoProducer userViewVideoProducer;
  private final UserPauseVideoProducer userPauseVideoProducer;
  private final UserSearchProducer userSearchProducer;
  private final UserViewCourseProducer userViewCourseProducer;
  private final UserOnlineDurationProducer userOnlineDurationProducer;
  private final UserLearningProgressProducer userLearningProgressProducer;

  @Override
  public UserBehaviorProducer create(UserBehaviorType behaviorType) {
    Objects.requireNonNull(behaviorType, "behaviorType must not be null");

    return switch (behaviorType) {
      case USER_CLICK_COURSE -> userClickCourseProducer;
      case USER_VIEW_VIDEO -> userViewVideoProducer;
      case USER_PAUSE_VIDEO -> userPauseVideoProducer;
      case USER_SEARCH -> userSearchProducer;
      case USER_VIEW_COURSE -> userViewCourseProducer;
      case USER_ONLINE_DURATION -> userOnlineDurationProducer;
      case USER_LEARNING_PROGRESS -> userLearningProgressProducer;
    };
  }
}
