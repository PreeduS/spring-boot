package com.example.demo.Topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;


    public List<Topic> getAllTopics(){
        List<Topic> topics = new ArrayList<>();
   
        topicRepository.findAll().forEach(topics::add);

        return topics;
    }

    public Topic addTopic(Topic topic){
        return topicRepository.save(topic);
    }
    public Topic updateTopic(Topic topic){
        return topicRepository.save(topic);
    }


    public Optional<Topic> getTopic(Long id){
        return topicRepository.findById(id);
    }
    public void deleteTopic(Long id){
        topicRepository.deleteById(id);
    }
}