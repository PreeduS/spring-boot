package com.example.demo.Exception;

public class _ValidationException extends RuntimeException { 
    private static final long serialVersionUID = 1L;
    private String message;
    public _ValidationException(String message){
        this.setMessage(message);
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
     
}