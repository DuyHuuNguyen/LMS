package com.james.LMS.service.impl;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateVideoPayload;
import com.james.LMS.service.ProducerCreateVideoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerCreateVideoServiceImpl implements ProducerCreateVideoService {

  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange-uploading-video}")
  private String uploadingVideoExchange;

  @Value("${rabbitmq.queue-uploading-video}")
  private String uploadingVideoQueue;

  @Value("${rabbitmq.routing-key-uploading-video}")
  private String uploadingVideoRoutingKey;

  @SneakyThrows
  @Override
  public void send(BaseMessage<CreateVideoPayload> message) {
    log.info("Producer send {}", message);
    this.rabbitTemplate.convertAndSend(uploadingVideoExchange, uploadingVideoRoutingKey, message);
  }
}
