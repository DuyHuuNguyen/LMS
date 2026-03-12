package com.james.LMS.response;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> implements Serializable {
  private List<T> data;
  private Integer currentPage;
  private Integer totalElements;
  private Integer totalPages;
}
