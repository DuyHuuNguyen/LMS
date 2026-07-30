package com.james.LMS.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningProgressRequest extends BaseRequest {
  @Positive
  @Min(1)
  private Integer currentPage;

  public Integer computeCurrentPage(){
    return this.currentPage -1;
  }
}
