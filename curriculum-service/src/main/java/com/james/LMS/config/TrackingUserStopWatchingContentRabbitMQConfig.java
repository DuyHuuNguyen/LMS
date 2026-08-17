package com.james.LMS.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrackingUserStopWatchingContentRabbitMQConfig {
  @Value("${rabbitmq.user-stop-watching-content-session.video-queue-name}")
  private String videoQueue;

  @Value("${rabbitmq.user-stop-watching-content-session.topic-exchange}")
  private String topicExchange;

  @Value("${rabbitmq.user-stop-watching-content-session.video-routing-key}")
  private String videoRoutingKey;

  @Value("${rabbitmq.user-stop-watching-content-session.exam-queue-name}")
  private String examQueue;

  @Value("${rabbitmq.user-stop-watching-content-session.exam-routing-key}")
  private String examRoutingKey;

  @Bean
  public TopicExchange userStopWatchingContentSessionExchange() {
    return new TopicExchange(topicExchange);
  }

  @Bean
  public Queue userStopWatchingVideoQueue() {
    return new Queue(this.videoQueue, true);
  }

  @Bean
  public Binding userStopWatchingVideoBinding() {
    return BindingBuilder.bind(this.userStopWatchingVideoQueue())
        .to(this.userStopWatchingContentSessionExchange())
        .with(this.videoRoutingKey);
  }

  @Bean
  public Queue userStopWatchingExamQueue() {
    return new Queue(this.examQueue, true);
  }

  @Bean
  public Binding userStopWatchingExamBinding() {
    return BindingBuilder.bind(this.userStopWatchingExamQueue())
        .to(this.userStopWatchingContentSessionExchange())
        .with(this.examRoutingKey);
  }
}
