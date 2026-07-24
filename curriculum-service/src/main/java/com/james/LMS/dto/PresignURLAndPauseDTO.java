package com.james.LMS.dto;

import lombok.Builder;

@Builder
public record PresignURLAndPauseDTO(String presignURL,Long pausedAt) {
}
