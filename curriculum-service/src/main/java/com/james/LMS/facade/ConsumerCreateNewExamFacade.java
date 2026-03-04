package com.james.LMS.facade;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateTestsPayload;

public interface ConsumerCreateNewExamFacade {
  void consume(BaseMessage<CreateTestsPayload> createTestsBaseMessage);
}
