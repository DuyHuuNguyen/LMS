package com.james.LMS.response;

import com.james.LMS.dto.ActiveCurrentSessionDTO;
import com.james.LMS.dto.SessionDTO;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class SessionDetailResponse {
  private ActiveCurrentSessionDTO activeCurrentSessionDTO;
  private List<SessionDTO> sessionDTOS;

  public void addActiveCurrentSessionDTO(ActiveCurrentSessionDTO activeCurrentSessionDTO) {
    this.activeCurrentSessionDTO = activeCurrentSessionDTO;
  }
}
