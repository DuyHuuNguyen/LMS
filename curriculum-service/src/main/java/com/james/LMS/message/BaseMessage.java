package com.james.LMS.message;

import com.james.LMS.enums.MessageType;
import com.james.LMS.enums.SourceMessageEnum;
import java.io.Serializable;
import java.time.Instant;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class BaseMessage<T> implements Serializable {
  private MessageType type;
  private Instant createdAt;
  private SourceMessageEnum source;
  private T payload;
}
