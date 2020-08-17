package com.example.demo.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Topic.Topic;
import com.example.demo.Topic.TopicRepository;


@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TopicRepository topicRepository;

    //@Autowired
    //private SessionFactory sessionFactory;
    


    public List<Course> getAllCourses(Long topicId){
        List<Course> courses = new ArrayList<>();
   
        //courseRepository.findAll().forEach(courses::add);
        courseRepository.findByTopicId(topicId).forEach(courses::add);

        return courses;
    }

    public Course addCourse(Course course){
        return courseRepository.save(course);               // insert or update
    }

    public CourseDtoInput addTopicWithCourses(CourseDtoInput courseDtoInput){
        System.out.println(courseDtoInput.getTopic().getName());
        System.out.println(courseDtoInput.getTopic().getId());
        Topic topicResult = topicRepository.save(courseDtoInput.getTopic());
        System.out.println(topicResult.getName());
        System.out.println(topicResult.getId());
            List <Course> updatedCourses = new ArrayList<Course>();
            courseDtoInput.getCourses().forEach(course ->{
                course.setTopic(topicResult.getId(), "test topic");
                updatedCourses.add(course);
            });
            List<Course> coursesResult = new ArrayList<Course>();
            courseRepository.saveAll(updatedCourses).forEach(coursesResult::add);

            CourseDtoInput result = new CourseDtoInput(coursesResult, topicResult);
            return result;


       /* List<Topic> updatedTopics = new ArrayList<Topic>();
        courseDtoInput.getCourses().forEach(topic ->{
            updatedTopics.add(new Topic(topic.getId(), topic.getName() ));
        });
        List<Topic> topicsResult = new ArrayList<Topic>();
        topicRepository.saveAll(updatedTopics).forEach(topicsResult::add);
*/
/*

        List<Topic> topicsResult = new ArrayList<Topic>();
        topicRepository.saveAll(updatedTopics).forEach(topicsResult::add);

        CourseDtoInput result = new CourseDtoInput(courseResult, topicResult);
       
         return result;*/
    }
    public List<Course> addCourse(List<Course> courses){
        List<Course> result = new ArrayList<Course>();

        courseRepository.saveAll(courses).forEach(result::add);
        return result;
    }
    public Course updateCourse(  Course course){
    //public Course updateCourse(Long id, Course course){
      //  course.setId(id);
     
        return courseRepository.save(course);
    }


    public Optional<Course> getCourse(Long id){
        return courseRepository.findById(id);
    }
    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }


}