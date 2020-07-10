package com.example.demo.Course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.Topic.Topic;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    

    public Course( ){
        super();
    }
    public Course(Long id, String name, String description, Long topicId){
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.topic = new Topic(topicId, "placeholder");
    }

    public void setTopic(Long topicId, String topicName){
        this.topic = new Topic(topicId, topicName);
    }

    public void setId(Long id){
       this.id = id;
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
    public Topic getTopic(){
       return topic;
    }
}