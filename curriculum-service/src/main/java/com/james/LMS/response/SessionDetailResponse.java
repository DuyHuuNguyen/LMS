package com.james.LMS.response;

import com.james.LMS.dto.SessionDTO;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class SessionDetailResponse {
  private List<SessionDTO> sessionDTOS;
}
