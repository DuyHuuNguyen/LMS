package com.james.LMS.service;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.LoadLecturerIntoCachePayload;

public interface ConsumerLoadLecturerIntoCacheService {
  void consume(BaseMessage<LoadLecturerIntoCachePayload> loadLecturerIntoCachePayloadMessage);
}
