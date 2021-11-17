package com.example.demo.Course;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.validators._Between;

public class _TestValidationDto {

    @NotNull
    @Size(min = 3, max = 5, message = "Size validation message")
    private String name;

    private String status;

    @_Between(min = 10, max = 100)
    private Integer age;

    public _TestValidationDto(String name, String status){
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