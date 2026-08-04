package com.james.LMS.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(
    prefix = "notification.sms",
    name = "enabled",
    havingValue = "Chua.code.nha.:))")
@Configuration
public class AutoTaggingRabbitMQConfig {
  @Value("${rabbitmq.auto-tagging.queue-name}")
  private String queue;

  @Value("${rabbitmq.auto-tagging.topic-exchange}")
  private String topic;

  @Value("${rabbitmq.auto-tagging.routing-key}")
  private String routingKey;

  @Bean
  public TopicExchange autoTaggingExchange() {
    return new TopicExchange(topic);
  }

  @Bean
  public Queue autoTaggingQueue() {
    return new Queue(queue, true);
  }

  @Bean
  public Binding autoTaggingBinding() {
    return BindingBuilder.bind(autoTaggingQueue()).to(autoTaggingExchange()).with(routingKey);
  }
}
