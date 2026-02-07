package com.james.LMS.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.james.LMS.enums.SessionContentEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class BaseSessionContentDTO {
  private Long id;
  private String name;
  private Boolean isPreView;
  private Integer index;
  private Long sessionId;

  protected SessionContentEnum type;

  @JsonIgnore
  public abstract Boolean isVideo();
}
