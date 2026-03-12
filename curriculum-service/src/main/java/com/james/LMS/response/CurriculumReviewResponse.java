package com.james.LMS.response;

import com.james.LMS.dto.InstructorDTO;
import com.james.LMS.dto.SessionDTO;
import com.james.LMS.dto.TopicDTO;
import java.math.BigDecimal;
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
  private InstructorDTO instructorDTO;
  private String title;
  private String headline;
  private BigDecimal cost;
  private String description;
  private String requirement;
  private List<TopicDTO> topicDTOS;
  private String totalTimesStringFormat;
  private Integer totalSessions;
  private Integer totalLectures;
  private List<SessionDTO> sessionDTOs;
}
