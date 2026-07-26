package com.james.LMS.service;

import com.james.LMS.enums.UserBehaviorType;
import com.james.LMS.service.producer.UserBehaviorProducer;

public interface FactoryProducerTrackingWatchingContentService {
  UserBehaviorProducer create(UserBehaviorType behaviorType);
}
