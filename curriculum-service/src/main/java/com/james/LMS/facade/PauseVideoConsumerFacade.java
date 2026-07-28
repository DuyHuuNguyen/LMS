package com.james.LMS.facade;

import com.james.LMS.message.final_lms_message.PauseVideoMessage;

public interface PauseVideoConsumerFacade {
  void receive(PauseVideoMessage pauseVideoMessage);
}
