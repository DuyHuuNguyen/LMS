package com.james.LMS.dto;

import com.james.LMS.message.final_lms_message.BaseQueueMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayloadCurriculumVectorMessage extends BaseQueueMessage {
    private Long userId;
    private String username;
    private String avatar;

    private Long id;
    private String title;
    private String headLine;
    private BigDecimal cost;
    private String description;
    private String requirement;
    private String thumbnail;
}
