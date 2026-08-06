package com.james.LMS.response;

import com.james.LMS.dto.TrainingSessionDTO;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DashBoardResponse {
  private String groupName;
  private Integer totalMembers;

  private Map<String, Integer> totalBothMultipleChoiceAndEssay;

  private List<TrainingSessionDTO> trainingSessionDTOS;
  private Integer totalTrainingSessions;
}
