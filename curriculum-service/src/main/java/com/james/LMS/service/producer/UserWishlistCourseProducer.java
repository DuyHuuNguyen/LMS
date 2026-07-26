package com.james.LMS.service.producer;

import com.james.LMS.message.final_lms_message.BaseQueueMessage;
import org.springframework.stereotype.Component;

@Component
public class UserWishlistCourseProducer implements UserBehaviorProducer {
  @Override
  public String behaviorType() {
    return "USER_WISHLIST_COURSE";
  }

  @Override
  public void produce(BaseQueueMessage message) {}
}
