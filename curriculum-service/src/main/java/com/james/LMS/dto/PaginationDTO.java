package com.james.LMS.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PaginationDTO<T> {
  private Integer currentPage;
  private Integer pageSize;
  private Integer totalItems;
  private List<T> data;
}
