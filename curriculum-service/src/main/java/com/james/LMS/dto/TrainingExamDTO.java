package com.james.LMS.dto;

import lombok.Builder;

@Builder
public record TrainingExamDTO(
        Long id,
        String examName
) {
}
