package com.example.demo.Handler;

import com.example.demo.Exception.InvalidHeaderFieldException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class HeaderExceptionHandler extends ResponseEntityExceptionHandler{
    
    //@ExceptionHandler(InvalidHeaderFieldException.class)
    /*public String handleInvalidFieldException(InvalidHeaderFieldException exception){
        return exception.getMessage();
    }*/

    public class HandleInvalidHeaderFieldExceptionDto {
        public String message;

        public HandleInvalidHeaderFieldExceptionDto(String message){
            this.message = message;
        }
    }


    @ExceptionHandler(InvalidHeaderFieldException.class)
    public ResponseEntity<HandleInvalidHeaderFieldExceptionDto> handleInvalidHeaderFieldException(InvalidHeaderFieldException exception){
        //return new ResponseEntity<>(exception.getMessage(),HttpStatus.PRECONDITION_FAILED);
        return new ResponseEntity<HandleInvalidHeaderFieldExceptionDto>(
            new HandleInvalidHeaderFieldExceptionDto(exception.getMessage()),
            HttpStatus.PRECONDITION_FAILED
        );
    }
}