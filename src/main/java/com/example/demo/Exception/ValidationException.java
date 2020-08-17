package com.example.demo.Exception;

public class ValidationException extends RuntimeException { 
    private static final long serialVersionUID = 1L;
    private String message;
    public ValidationException(String message){
        this.setMessage(message);
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
     
}