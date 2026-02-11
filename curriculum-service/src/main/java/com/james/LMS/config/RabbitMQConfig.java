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

  @Value("${rabbitmq.cache-data-queue}")
  private String cacheDataQueue;

  @Value("${rabbitmq.cache-data-routing-key}")
  private String cacheDataRoutingKey;

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  public Queue cacheDataQueue() {
    return new Queue(cacheDataQueue, true);
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
  public Binding cacheDataBinding() {
    return BindingBuilder.bind(cacheDataQueue()).to(exchange()).with(cacheDataRoutingKey);
  }
}
