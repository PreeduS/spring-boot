package com.example.demo.Course;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TestValidationDto {

    @NotNull
    @Size(min = 3, max = 5, message = "Size validation message")
    private String name;

    private String status;

    public TestValidationDto(String name, String status){
        this.name = name;
        this.status = status;
    }

    public String getName(){
        return name;
    }
    public String getStatus(){
        return status;
    }
    
}