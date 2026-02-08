package com.james.LMS.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CurriculumDTO {
    private Long userId;
    private String username;
    private String title;
    private String headLine;
    private BigDecimal cost;
    private String description;
    private String name;
    private String thumbnail;
    private String topicName;
}
