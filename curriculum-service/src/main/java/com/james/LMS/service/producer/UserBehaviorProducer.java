package com.james.LMS.service.producer;

import com.james.LMS.message.final_lms_message.BaseQueueMessage;

/**
 * Identifies a producer for a specific user-behavior event.
 *
 * <p>The event payload and transport are intentionally left to the caller's integration layer.
 */
public interface UserBehaviorProducer {

  String behaviorType();

  void produce(BaseQueueMessage message);
}
