package com.james.LMS.service.impl;

import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateTestsPayload;
import com.james.LMS.service.ProducerCreateNewExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerCreateNewExamServiceImpl implements ProducerCreateNewExamService {
  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange-create-tests-name}")
  private String createTestsExchange;

  @Value("${rabbitmq.create-tests-routing-key}")
  private String createTestsRoutingKey;

  @Override
  public void send(BaseMessage<CreateTestsPayload> createTestsBaseMessage) {
    log.info("Producer send {}", createTestsBaseMessage);
    this.rabbitTemplate.convertAndSend(
        createTestsExchange, createTestsRoutingKey, createTestsBaseMessage);
  }
}
