package com.james.LMS.service;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateTestsPayload;

public interface ProducerCreateNewExamService {
  void send(BaseMessage<CreateTestsPayload> createTestsBaseMessage);
}
