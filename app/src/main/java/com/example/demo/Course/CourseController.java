package com.example.demo.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CriteriaQueryExService criteriaService;

    @GetMapping("/topic/{id}/courses")
    public List<Course> getAllCourses(@PathVariable Long id) {
        return courseService.getAllCourses(id);
    }

    @GetMapping("/topic/{topicId}/courses/{id}")
    public Optional<Course> getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }
    @PostMapping("/topic/{topicId}/course")
    public Course addCourse(@PathVariable Long topicId, @RequestBody Course course) {
        course.setTopic(topicId, "topic" + course.getName() );
        return courseService.addCourse(course);
    }
    @PostMapping("/topic/{topicId}/courses")
    public List<Course> addCourse(@PathVariable Long topicId, @RequestBody List<Course> courses) {
         List<Course> updatedCourses = new ArrayList<Course>();
        courses.forEach(course->{
            course.setTopic(topicId, "topic" + course.getName() );
            updatedCourses.add(course);
        });

        return courseService.addCourse(updatedCourses);
    }
    @PostMapping("/topic/courses")
    public CourseDtoInput addCourse( @RequestBody CourseDtoInput courseDtoInput) {
        

        return courseService.addTopicWithCourses(courseDtoInput);
    }
    @PutMapping("/topic/{topicId}/courses/{courseId}")
    public Course updateCourse(@PathVariable Long topicId,@PathVariable Long courseId, @RequestBody Course course) {
        course.setTopic(topicId, "");                
        
        return courseService.updateCourse(course);
        //return courseService.updateCourse(courseId,course);
    }
    @DeleteMapping("/topic/{topicId}/courses/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
       
        courseService.deleteCourse(courseId);
    }


    @PostMapping("/_updateCourse/{courseId}")
    public void _updateCourse(@PathVariable String courseId) {
       
        criteriaService._updateCourse(courseId);
    }
    @DeleteMapping("/_deleteCourse/{courseId}")
    public void _deleteCourse(@PathVariable String courseId) {
       
        criteriaService._deleteCourse(courseId);
    }


    @GetMapping("/temp")
    public void temp( ) {
       
        criteriaService.temp8();
    }

    //


}