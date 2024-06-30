package com.example.coursecompass.service;

import com.example.coursecompass.repository.CourseRepository;
import com.example.coursecompass.model.Course;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    // private List<Course> courses;

    @PostConstruct
    public void init() {
        try {
            if (courseRepository.count() == 0) {
                ObjectMapper mapper = new ObjectMapper();
                File jsonFile = new File("src/main/python/data/courses.json");
                List<Course> courses = mapper.readValue(jsonFile, new TypeReference<List<Course>>() {});
                courseRepository.saveAll(courses);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCourses() {
        //return courses;
        return courseRepository.findAll(); //new
    }

    public Course findCourseByCode(String courseCode) {
//        return courses.stream()
//                .filter(course -> course.getCourse_code().equalsIgnoreCase(courseCode))
//                .findFirst()
//                .orElse(null);
        return courseRepository.findByCourseCode(courseCode); // new
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }
}
