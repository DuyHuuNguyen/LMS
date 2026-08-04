package com.james.LMS.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class SlicePaginationResponse<T> {
  private Integer currentPage;
  private List<T> data;
}
