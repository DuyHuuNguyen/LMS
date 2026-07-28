package com.james.LMS.facade.impl;

import com.james.LMS.facade.PauseVideoConsumerFacade;
import com.james.LMS.message.final_lms_message.PauseVideoMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PauseVideoConsumerFacadeImpl implements PauseVideoConsumerFacade {

  @Override
  public void receive(PauseVideoMessage pauseVideoMessage) {
    log.info("Received message : {}", pauseVideoMessage);
  }
}
