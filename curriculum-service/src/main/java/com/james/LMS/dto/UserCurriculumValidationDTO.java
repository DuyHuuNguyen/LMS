package com.james.LMS.dto;

import com.james.LMS.enums.WatchingContentType;
import lombok.Builder;

@Builder
public record UserCurriculumValidationDTO (Long userId, Long curriculumId, Long contentId, WatchingContentType type){
}
