package com.james.LMS.service;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateTestsPayload;

public interface ConsumerCreateNewExamService {
  void consume(BaseMessage<CreateTestsPayload> createTestsBaseMessage);
}
