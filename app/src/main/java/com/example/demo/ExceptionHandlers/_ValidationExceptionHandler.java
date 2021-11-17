package com.example.demo.ExceptionHandlers;

import com.example.demo.Exception._ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class _ValidationExceptionHandler {
    
    @ExceptionHandler(_ValidationException.class)
    public ResponseEntity<_ValidationExceptionDto> handleInvalidHeaderFieldException(_ValidationException exception){ 
        return new ResponseEntity<_ValidationExceptionDto>(
            new _ValidationExceptionDto(exception.getMessage()),
            HttpStatus.PRECONDITION_FAILED
        );
    }
}