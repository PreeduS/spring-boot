package com.example.demo.Topic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.demo.Course.Course;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
    private Long id;
    private String name;

    @OneToMany(mappedBy = "topic")
    List<Course> courses = new ArrayList<Course>();
    // @OneToMany,  mappedBy topic

    public Topic(){
        super();
    }
    public Topic(Long id, String name){
        super();
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Course> getCourses() {
        return courses;
    }

}