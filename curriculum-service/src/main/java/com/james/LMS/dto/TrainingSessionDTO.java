package com.james.LMS.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TrainingSessionDTO(Long id,
                                 String name,
                                 String startedAt,
                                 String endedAt,
                                 boolean isCompleted,
                                 List<CurriculumTrainingSetDTO> curriculumTrainingSetDTOS,
                                 List<TrainingExamDTO> trainingExamDTOS) {
}
