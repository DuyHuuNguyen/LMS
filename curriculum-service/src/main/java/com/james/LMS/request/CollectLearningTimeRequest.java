package com.james.LMS.request;

import com.james.LMS.enums.WatchingContentType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CollectLearningTimeRequest {

  @Positive private Long contentId;
  @Positive private Long curriculumId;

  @Positive
  @Min(1)
  private Integer learningMinutes;

  @NotNull private WatchingContentType type;
}
