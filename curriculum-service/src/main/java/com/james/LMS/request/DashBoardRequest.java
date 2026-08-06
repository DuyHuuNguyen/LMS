package com.james.LMS.request;

import io.swagger.v3.oas.annotations.Hidden;
import java.time.Month;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardRequest extends BaseRequest {
  @Hidden private Long groupId;
  private Long companyId;

  private Month month;

  public void withGroupId(Long id) {
    this.groupId = id;
  }
}
