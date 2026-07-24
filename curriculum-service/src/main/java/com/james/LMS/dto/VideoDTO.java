package com.james.LMS.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.james.LMS.enums.SessionContentEnum;
import com.james.LMS.util.DurationConverterUtil;
import java.time.Duration;
import lombok.*;

@NoArgsConstructor
@Getter
@ToString
public class VideoDTO extends BaseSessionContentDTO {
  @JsonIgnore private Integer durationSeconds;

  public VideoDTO(
      Long id,
      String name,
      Boolean isPreview,
      Integer index,
      Long sessionId,
      Integer durationSeconds) {
    super(id, name, isPreview, index, sessionId, SessionContentEnum.VIDEO);
    this.durationSeconds = durationSeconds;
  }

  @Override
  public Boolean isVideo() {
    return true;
  }

  @JsonGetter("duration")
  public String getDurationFormatted() {
    return DurationConverterUtil.toStringDuration(Duration.ofSeconds(this.durationSeconds));
  }
}
