package com.james.LMS.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeaningProgressRabbitMQConfig {
  @Value("${rabbitmq.learning-progress.queues.queue-video}")
  private String videoQueue;

  @Value("${rabbitmq.learning-progress.queues.queue-exam}")
  private String examQueue;

  @Value("${rabbitmq.learning-progress.topic-exchange}")
  private String topic;

  @Value("${rabbitmq.learning-progress.routing-keys.video}")
  private String videoRoutingKey;

  @Value("${rabbitmq.learning-progress.routing-keys.exam}")
  private String examRoutingKey;

  @Bean
  public TopicExchange learningProgressExchange() {
    return new TopicExchange(topic);
  }

  @Bean
  public Queue learningProgressVideoQueue() {
    return new Queue(videoQueue, true);
  }

  @Bean
  public Queue learningProgressExamQueue() {
    return new Queue(examQueue, true);
  }

  @Bean
  public Binding videoBinding() {
    return BindingBuilder.bind(learningProgressVideoQueue())
        .to(learningProgressExchange())
        .with(videoRoutingKey);
  }

  @Bean
  public Binding examBinding() {
    return BindingBuilder.bind(learningProgressExamQueue())
        .to(learningProgressExchange())
        .with(examRoutingKey);
  }
}
