package com.finpro.grocery.share.exception;

public class InternalServerException extends RuntimeException {
  public InternalServerException(String message) {
    super(message);
  }
}