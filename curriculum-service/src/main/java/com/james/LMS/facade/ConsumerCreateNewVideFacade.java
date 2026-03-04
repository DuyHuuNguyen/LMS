package com.james.LMS.facade;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateVideoPayload;

public interface ConsumerCreateNewVideFacade {
  void consume(BaseMessage<CreateVideoPayload> createVideosBaseMessage);
}
