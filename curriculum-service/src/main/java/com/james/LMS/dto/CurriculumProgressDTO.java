package com.james.LMS.dto;

import lombok.Builder;

@Builder
public record CurriculumProgressDTO(Long curriculumId,String title, Float percents) {
}
