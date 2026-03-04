package com.james.LMS.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class CurriculumDTO {
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
  private Long topicId;
  private String topicName;

  public void addLectureInfo(InstructorDTO lecturerDTO) {
    this.userId = lecturerDTO.getUserId();
    this.username = lecturerDTO.getUsername();
    this.avatar = lecturerDTO.getAvatar();
  }

  @JsonIgnore
  public TopicDTO getTopicDTO() {
    return TopicDTO.builder().id(this.topicId).name(this.topicName).build();
  }
}
