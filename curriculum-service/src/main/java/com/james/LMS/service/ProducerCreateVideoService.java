package com.james.LMS.service;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateVideoPayload;

public interface ProducerCreateVideoService {
  void send(BaseMessage<CreateVideoPayload> message);
}
