package com.james.LMS.dto;

import com.james.LMS.enums.SessionContentEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@SuperBuilder
public class ExamDTO extends BaseSessionContentDTO {
  private final SessionContentEnum type = SessionContentEnum.EXAM;

  public ExamDTO(Long id, String name, Boolean isPreview, Integer index, Long sessionId) {
    super(id, name, isPreview, index, sessionId, SessionContentEnum.EXAM);
  }

  @Override
  public Boolean isVideo() {
    return false;
  }
}
