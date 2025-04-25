package com.evision.FileMatcher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// this class is for handling exception for the controller
@ControllerAdvice
public class GlobalExceptionHandler {

    //exception Handler for Custom Exception
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> HandleException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
