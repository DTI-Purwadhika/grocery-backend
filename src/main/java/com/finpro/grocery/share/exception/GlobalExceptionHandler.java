package com.finpro.grocery.share.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.finpro.grocery.share.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ApiResponse<String>> handleUnauthorizedException(UnauthorizedException ex) {
    ApiResponse<String> response = new ApiResponse<>("UNAUTHORIZED", "401", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex) {
    ApiResponse<String> response = new ApiResponse<>("BAD REQUEST", "400", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    ApiResponse<String> response = new ApiResponse<>("BAD REQUEST", "400", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ApiResponse<String>> handleForbiddenException(ForbiddenException ex) {
    ApiResponse<String> response = new ApiResponse<>("FORBIDDEN", "403", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
    ApiResponse<String> response = new ApiResponse<>("NOT FOUND", "404", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<ApiResponse<String>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
    ApiResponse<String> response = new ApiResponse<>("CONFLICT", "409", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<ApiResponse<String>> handleInternalServerException(InternalServerException ex) {
    ApiResponse<String> response = new ApiResponse<>("INTERNAL SERVER ERROR", "500", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
    ApiResponse<String> response = new ApiResponse<>("INTERNAL SERVER ERROR", "500", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}