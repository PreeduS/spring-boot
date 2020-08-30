package com.example.demo.controllers;


public class Post {
    private Integer userId;
    private String id;
    //String title;
    //String body;
    public Post(){

    }
    public Post(Integer userId){
        this.userId = userId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public Integer getUserId(){
        return userId;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
}