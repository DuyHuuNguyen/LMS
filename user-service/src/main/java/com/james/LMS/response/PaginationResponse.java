package com.james.LMS.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {
  private List<T> data;
  private int currentPage;
  private int totalElements;
  private int totalPages;
}
