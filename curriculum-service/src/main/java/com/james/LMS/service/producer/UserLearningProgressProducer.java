package com.james.LMS.service.producer;

import com.james.LMS.message.final_lms_message.BaseQueueMessage;
import com.james.LMS.message.final_lms_message.LearningProgressMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLearningProgressProducer implements UserBehaviorProducer {

  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.learning-progress.topic-exchange}")
  private String exchange;

  @Value("${rabbitmq.learning-progress.routing-keys.video}")
  private String videoRoutingKey;

  @Value("${rabbitmq.learning-progress.routing-keys.exam}")
  private String examRoutingKey;

  @Override
  public String behaviorType() {
    return "USER_LEARNING_PROGRESS";
  }

  @Override
  public void produce(BaseQueueMessage message) {
    if (message instanceof LearningProgressMessage learningProgressMessage) {
      log.info("Produce message : {}",message.toJsonString());
      switch (learningProgressMessage.getType()) {
        case EXAM -> rabbitTemplate.convertAndSend(
                exchange,
                examRoutingKey,
                message
        );

        case VIDEO -> rabbitTemplate.convertAndSend(
                exchange,
                videoRoutingKey,
                message
        );
      }
    }
  }
}
