package com.james.LMS.service.producer;

import com.james.LMS.message.final_lms_message.BaseQueueMessage;
import com.james.LMS.message.final_lms_message.StopWatchingSessionContentMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPauseVideoProducer implements UserBehaviorProducer {
  @Override
  public String behaviorType() {
    return "USER_PAUSE_VIDEO";
  }

  private final RabbitTemplate rabbitTemplate;


  @Value("${rabbitmq.user-stop-watching-content-session.topic-exchange}")
  private String topicExchange;

  @Value("${rabbitmq.user-stop-watching-content-session.video-routing-key}")
  private String videoRoutingKey;


  @Value("${rabbitmq.user-stop-watching-content-session.exam-routing-key}")
  private String examRoutingKey;

  @Override
  public void produce(BaseQueueMessage message) {
    log.info("Send message {}",message.toJsonString());

    if (message instanceof StopWatchingSessionContentMessage stopWatchingSessionContentMessage){
      switch (stopWatchingSessionContentMessage.getContentType()){
          case EXAM ->{
            this.rabbitTemplate.convertAndSend(this.topicExchange,this.examRoutingKey,stopWatchingSessionContentMessage);
          }
          case VIDEO -> {
            this.rabbitTemplate.convertAndSend(this.topicExchange,this.videoRoutingKey,stopWatchingSessionContentMessage);
          }
      }
    }
  }
}
