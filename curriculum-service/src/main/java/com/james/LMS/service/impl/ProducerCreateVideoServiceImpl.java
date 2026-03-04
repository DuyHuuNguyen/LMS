package com.james.LMS.service.impl;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateVideoPayload;
import com.james.LMS.service.ProducerCreateVideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerCreateVideoServiceImpl implements ProducerCreateVideoService {

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void send(BaseMessage<CreateVideoPayload> message) {
    log.info("Producer send {}", message);
    //        this.rabbitTemplate.convertAndSend(message);
  }
}
