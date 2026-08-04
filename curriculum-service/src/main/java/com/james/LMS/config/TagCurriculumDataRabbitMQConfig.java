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
public class TagCurriculumDataRabbitMQConfig {
  @Value("${rabbitmq.tag-curriculum-data.queue-name}")
  private String queue;

  @Value("${rabbitmq.tag-curriculum-data.topic-exchange}")
  private String topic;

  @Value("${rabbitmq.tag-curriculum-data.routing-key}")
  private String routingKey;

  @Bean
  public TopicExchange tagCurriculumDataExchange() {
    return new TopicExchange(topic);
  }

  @Bean
  public Queue tagCurriculumDataQueue() {
    return new Queue(queue, true);
  }

  @Bean
  public Binding tagCurriculumDataBinding() {
    return BindingBuilder.bind(tagCurriculumDataQueue())
        .to(tagCurriculumDataExchange())
        .with(routingKey);
  }
}
