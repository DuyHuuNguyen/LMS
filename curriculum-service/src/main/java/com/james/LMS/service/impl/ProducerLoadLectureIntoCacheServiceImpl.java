package com.james.LMS.service.impl;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.LoadLecturerIntoCachePayload;
import com.james.LMS.service.ProducerLoadLecturesIntoCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerLoadLectureIntoCacheServiceImpl
    implements ProducerLoadLecturesIntoCacheService {
  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange-name}")
  private String exchange;

  @Value("${rabbitmq.cache-data-queue}")
  private String cacheDataQueue;

  @Value("${rabbitmq.cache-data-routing-key}")
  private String cacheDataRoutingKey;

  @Override
  public void send(BaseMessage<LoadLecturerIntoCachePayload> loadLecturerIntoCacheMessage) {
    log.info("Producer send {}", loadLecturerIntoCacheMessage);
    this.rabbitTemplate.convertAndSend(exchange, cacheDataRoutingKey, loadLecturerIntoCacheMessage);
  }
}
