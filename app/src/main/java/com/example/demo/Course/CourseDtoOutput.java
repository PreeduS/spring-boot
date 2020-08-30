package com.example.demo.Course;

public class CourseDtoOutput {
    private Long id;
    private String name;
    private String description;

 
    public CourseDtoOutput(Long id, String name){
        super();
        this.id = id;
        this.name = name;
    }
    public CourseDtoOutput(Long id, String name, String description){
        super();
        this.id = id;
        this.name = name;
        this.description = description; 
    }
    public Long getId(){
        return id;
     }
     public String getName(){
        return name;
     }
     public String getDescription(){
        return description;
     }
}