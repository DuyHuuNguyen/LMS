package com.james.LMS.dto;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record PaginationCurriculumMapDTO (
        List<List<CurriculumDTO>> curriculumDTOSList,
        Map<String, PaginationDTO<CurriculumDTO>> topicNameCurriculumDTOMap
){

}
