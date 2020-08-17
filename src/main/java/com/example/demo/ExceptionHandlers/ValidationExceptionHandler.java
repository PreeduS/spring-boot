package com.example.demo.ExceptionHandlers;

import com.example.demo.Exception.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationExceptionDto> handleInvalidHeaderFieldException(ValidationException exception){ 
        return new ResponseEntity<ValidationExceptionDto>(
            new ValidationExceptionDto(exception.getMessage()),
            HttpStatus.PRECONDITION_FAILED
        );
    }
}