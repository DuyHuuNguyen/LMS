package com.james.LMS.response;

import com.james.LMS.dto.InstructorDTO;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SearchCurriculumResponse {
  private Long userId;
  private String username;
  private String avatar;

  private Long id;
  private String title;
  private String headLine;
  private BigDecimal cost;
  private String description;
  private String requirement;
  private String thumbnail;

  private Boolean isWishlisted;

  public void addLectureInfo(InstructorDTO lecturerDTO) {
    this.userId = lecturerDTO.getUserId();
    this.username = lecturerDTO.getUsername();
    this.avatar = lecturerDTO.getAvatar();
  }
}
