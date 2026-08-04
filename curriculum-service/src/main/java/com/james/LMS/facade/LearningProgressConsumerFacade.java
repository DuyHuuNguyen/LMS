package com.james.LMS.facade;

import com.james.LMS.message.final_lms_message.LearningProgressMessage;

public interface LearningProgressConsumerFacade {
  void handleVideo(LearningProgressMessage message);

  void handleExam(LearningProgressMessage message);
}
