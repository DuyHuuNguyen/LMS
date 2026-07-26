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

  @Builder.Default private Long createdAt = Instant.now().toEpochMilli();

  @Builder.Default
  private String serviceProducerMessage = "curriculum-service";

  @Builder.Default private String xRequestId = MDC.get("requestId").toString();

  private String messageName;

  @Override
  @SneakyThrows
  public String toJsonString() {
    return ObjectMapperUtil.OBJECT_MAPPER.writeValueAsString(this);
  }
}
