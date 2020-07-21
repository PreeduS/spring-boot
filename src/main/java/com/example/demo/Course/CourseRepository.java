package com.example.demo.Course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    
    // resolved by convention
    // findByName(String name), findByDescription
    // findByTopicId(String id),                // topic - object, id - property inside

    public List<Course> findByTopicId(Long topicId);
}