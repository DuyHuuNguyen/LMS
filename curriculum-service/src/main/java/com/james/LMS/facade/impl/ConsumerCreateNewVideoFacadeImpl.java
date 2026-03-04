package com.james.LMS.facade.impl;

import com.james.LMS.facade.ConsumerCreateNewVideFacade;
import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateVideoPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerCreateNewVideoFacadeImpl implements ConsumerCreateNewVideFacade {

  @Override
  @RabbitHandler
  //  @RabbitListener(queues = {"${rabbitmq.create-videos-queue}"})
  public void consume(BaseMessage<CreateVideoPayload> createVideosBaseMessage) {
    log.info("");
  }
}
