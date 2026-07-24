package com.james.LMS.interceptor;

import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.InvalidTokenException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.exception.VideoAlreadyExistInStorageException;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ExceptionResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestHandlerException {

  @ExceptionHandler(java.util.concurrent.ExecutionException.class)
  public ResponseEntity<BaseResponse<ExceptionResponse>> handleCompletionException(
          ExecutionException ex) {

    Throwable cause = ex.getCause();

    if (cause instanceof EntityNotFoundException e) {
      return handleEntityNotFoundException(e);
    }

    if (cause instanceof PermissionDeniedException e) {
      return handlePermissionDeniedException(e);
    }

    return new ResponseEntity<>(
            BaseResponse.build(
                    new ExceptionResponse("23130075", "Server is error, Call dev right now"), false),
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(java.util.concurrent.CompletionException.class)
  public ResponseEntity<BaseResponse<ExceptionResponse>> handleCompletionException(
          CompletionException ex) {

    Throwable cause = ex.getCause();

    if (cause instanceof EntityNotFoundException e) {
      return handleEntityNotFoundException(e);
    }

    if (cause instanceof PermissionDeniedException e) {
      return handlePermissionDeniedException(e);
    }

    return new ResponseEntity<>(
            BaseResponse.build(
                    new ExceptionResponse("23130370", "Server is error, Call dev right now"), false),
            HttpStatus.BAD_REQUEST);
  }

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

  @ExceptionHandler(VideoAlreadyExistInStorageException.class)
  public ResponseEntity<BaseResponse<ExceptionResponse>> handleVideoAlreadyExistOnStorageException(
      VideoAlreadyExistInStorageException exception) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(exception.getErrorCode(), exception.getMessage()), false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
    return new ResponseEntity<>(
        BaseResponse.build(extractErrors(ex), false), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<?> handleBindValidation(BindException ex) {
    return new ResponseEntity<>(
        BaseResponse.build(extractErrors(ex), false), HttpStatus.BAD_REQUEST);
  }

  private Map<String, String> extractErrors(BindException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
    return errors;
  }
}
