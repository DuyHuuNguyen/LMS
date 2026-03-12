package com.james.LMS.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PurchasedCurriculumResponse {
  private Long id;
  private String title;
  private String headline;
  private String description;
  private String curriculumThumbnail;

  @Schema(
      description =
          "If u is the fist time to learn curriculum, Progress curriculum data  is unavailable")
  private Boolean isFirstTimeLearnCurriculum;

  private String sessionName;
  private Long sessionId;

  @Schema(description = "This ID can a videoId or examId, You can see isVideo variable to know")
  private Long sessionContentId;

  private Boolean isVideo;
  private String stoppedAt;
}
