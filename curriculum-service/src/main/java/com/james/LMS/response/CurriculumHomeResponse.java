package com.james.LMS.response;

import com.james.LMS.dto.CurriculumDTO;
import com.james.LMS.dto.PaginationDTO;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CurriculumHomeResponse {
  Map<String, PaginationDTO<CurriculumDTO>> topicNamePaginationDTOMap;
}
