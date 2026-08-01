package com.james.LMS.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QdrantSearchRabbitMQConfig {

    @Value("${rabbitmq.qdrant-search.queue-name}")
    private String queue;

    @Value("${rabbitmq.qdrant-search.topic-exchange}")
    private String topic;

    @Value("${rabbitmq.qdrant-search.routing-key}")
    private String routingKey;

    @Bean
    public TopicExchange qdrantSearchExchange() {
        return new TopicExchange(topic);
    }

    @Bean
    public Queue qdrantSearchQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public Binding qdrantSearchBinding() {
        return BindingBuilder.bind(this.qdrantSearchQueue()).to(this.qdrantSearchExchange()).with(routingKey);
    }

}
