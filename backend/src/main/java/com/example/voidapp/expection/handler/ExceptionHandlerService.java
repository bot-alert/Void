package com.example.voidapp.expection.handler;

import com.example.voidapp.expection.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerService extends ResponseEntityExceptionHandler {
  @ExceptionHandler(value = {UserException.class})
  protected ResponseEntity<Object> handleConflict(UserException ex) {
    return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
  }
}
