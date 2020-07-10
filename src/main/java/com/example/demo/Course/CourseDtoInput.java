package com.example.demo.Course;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Topic.Topic;

// AddCourseDto
// AddCourseResultDto
public class CourseDtoInput {
    private List<Course> courses =  new ArrayList<Course>();
    private Topic topic;

 
    public CourseDtoInput(List<Course> courses, Topic topic){
        super();
        this.courses = courses;
        this.topic = topic;
    }

    public List<Course> getCourses(){
        return courses;
    }
    public Topic getTopic(){
        return topic;
    }
}