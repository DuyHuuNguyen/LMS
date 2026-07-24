package com.james.LMS.message;

import com.james.LMS.enums.MessageType;
import com.james.LMS.enums.SourceMessageEnum;
import java.io.Serializable;
import java.time.Instant;
import lombok.*;
import org.jboss.logging.MDC;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class BaseMessage<T> implements Serializable {
  private MessageType type;
  private Instant createdAt;
  private SourceMessageEnum source;
  @Builder.Default
  private String XRequestId = MDC.get("requestId").toString();
  private T payload;
}
