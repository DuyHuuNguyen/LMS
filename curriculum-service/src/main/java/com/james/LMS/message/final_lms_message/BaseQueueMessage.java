package com.james.LMS.message.final_lms_message;

import com.james.LMS.enums.MessageType;
import com.james.LMS.enums.SourceMessageEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jboss.logging.MDC;

import java.io.Serializable;
import java.time.Instant;

@Builder
@Getter
@ToString
public abstract class BaseQueueMessage implements Serializable, JsonStringAble {
    private MessageType type;
    @Builder.Default
    private Instant createdAt = Instant.now();
    private String serviceName;
    @Builder.Default
    private String xRequestId = MDC.get("requestId").toString();
}
