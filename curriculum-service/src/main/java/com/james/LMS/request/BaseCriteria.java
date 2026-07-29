package com.james.LMS.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseCriteria {

  @NotNull
  @Min(1)
  private Integer currentPage;

  @NotNull @Positive private Integer pageSize;

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  public Integer getCurrentPage() {
    return currentPage - 1;
  }
}
