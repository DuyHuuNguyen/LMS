package com.james.LMS.response;

import com.james.LMS.dto.SessionDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CurriculumReviewResponse {
  private String totalTimesStringFormat;
  private Integer totalSessions;
  private Integer totalLectures;
  private List<SessionDTO> sessionDTOs;
}
