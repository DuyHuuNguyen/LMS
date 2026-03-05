package com.james.LMS.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class BaseResponse<T> implements Serializable {
  private boolean isSuccess;
  private T metadata;

  public static <T> BaseResponse<T> build(T data, boolean isSuccess) {
    return (BaseResponse<T>) BaseResponse.builder().isSuccess(isSuccess).metadata(data).build();
  }

  public static <T> BaseResponse<T> ok() {
    return (BaseResponse<T>) BaseResponse.builder().isSuccess(true).metadata("Success").build();
  }
}
