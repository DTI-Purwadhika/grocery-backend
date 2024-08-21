package com.finpro.grocery.share.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
  private String status;
  private String code;
  private T data;

  public ApiResponse(String status, String code, T data) {
    this.status = status;
    this.code = code;
    this.data = data;
  }
}
