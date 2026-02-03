package com.james.LMS.service.impl;

import com.james.LMS.dto.MessageMailDTO;
import com.james.LMS.service.MailProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailProducerServiceImpl implements MailProducerService {
  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange-name}")
  private String exchange;

  @Value("${rabbitmq.user-mail-queue}")
  private String userMailQueue;

  @Value("${rabbitmq.user-mail-routing-key}")
  private String userMailRoutingKey;

  @Override
  public void send(MessageMailDTO messageMailDTO) {
    log.info("Producer : Messages mail {}", messageMailDTO);
    this.rabbitTemplate.convertAndSend(exchange, userMailRoutingKey, messageMailDTO);
  }
}
