package com.james.LMS.message.final_lms_message;

import com.james.LMS.util.ObjectMapperUtil;
import java.io.Serializable;
import java.time.Instant;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jboss.logging.MDC;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseQueueMessage implements Serializable, JsonStringAble {

  private Long createdAt;

  private String serviceProducerMessage;

  private String requestId;

  private String messageName;

  @Override
  @SneakyThrows
  public String toJsonString() {
    return ObjectMapperUtil.OBJECT_MAPPER.writeValueAsString(this);
  }

  public void initialBaseInfoMessage() {
    this.requestId =  MDC.get("requestId") == null ? "internal request by system" : MDC.get("requestId").toString() ;
    this.serviceProducerMessage = "curriculum-service";
    this.createdAt = Instant.now().toEpochMilli();
  }
}
