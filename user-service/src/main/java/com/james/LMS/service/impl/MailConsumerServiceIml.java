package com.james.LMS.service.impl;

import com.james.LMS.dto.MessageMailDTO;
import com.james.LMS.service.EmailService;
import com.james.LMS.service.MailConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailConsumerServiceIml implements MailConsumerService {
  private final EmailService emailService;

  @Override
  @RabbitHandler
  @RabbitListener(queues = {"${rabbitmq.user-mail-queue}"})
  public void consume(MessageMailDTO messageMailDTO) {
    log.info("Consumer : Messages mail {}", messageMailDTO);
    this.emailService.send(messageMailDTO);
  }
}
