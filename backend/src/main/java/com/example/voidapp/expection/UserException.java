package com.example.voidapp.expection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserException extends RuntimeException {
  private final String message;
  private final HttpStatus httpStatus;

  public UserException(String message, HttpStatus httpStatus) {
    super(message);
    this.message = message;
    this.httpStatus = httpStatus;
  }

}
