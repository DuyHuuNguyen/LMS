package com.james.LMS.service;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.LoadLecturerIntoCachePayload;

public interface ProducerLoadLecturesIntoCacheService {
  void send(BaseMessage<LoadLecturerIntoCachePayload> baseMessage);
}
