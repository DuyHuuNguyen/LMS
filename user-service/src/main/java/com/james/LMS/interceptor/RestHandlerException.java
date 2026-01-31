package com.james.LMS.interceptor;

import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.InvalidOTPException;
import com.james.LMS.exception.InvalidTokenException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestHandlerException {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<BaseResponse<ExceptionResponse>> handleEntityNotFoundException(
      EntityNotFoundException exception) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(exception.getErrorCode(), exception.getMessage()), false),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PermissionDeniedException.class)
  public ResponseEntity<BaseResponse<ExceptionResponse>> handlePermissionDeniedException(
      PermissionDeniedException exception) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(exception.getErrorCode(), exception.getMessage()), false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<BaseResponse<ExceptionResponse>> handleInvalidTokenException(
      InvalidTokenException exception) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(exception.getErrorCode(), exception.getMessage()), false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidOTPException.class)
  public ResponseEntity<BaseResponse<ExceptionResponse>> handleInvalidOTPException(
      InvalidOTPException exception) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(exception.getErrorCode(), exception.getMessage()), false),
        HttpStatus.BAD_REQUEST);
  }
}
