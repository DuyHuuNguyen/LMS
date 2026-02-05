package com.james.LMS.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.exchange-name}")
  private String exchange;

  @Value("${rabbitmq.user-mail-queue}")
  private String userMailQueue;

  @Value("${rabbitmq.user-mail-routing-key}")
  private String userMailRoutingKey;

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  public Queue userMailQueue() {
    return new Queue(userMailQueue, false);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return JsonMapper.builder().addModule(new JavaTimeModule()).build();
  }

  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Binding userMailBinding() {
    return BindingBuilder.bind(userMailQueue()).to(exchange()).with(userMailRoutingKey);
  }
}
