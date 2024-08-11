package com.example.coursecompass.service;

import com.example.coursecompass.dao.CourseDao;
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
    private CourseDao courseDao;

    @PostConstruct
    public void init() {
        try {
            if (courseDao.count() == 0) {
                ObjectMapper mapper = new ObjectMapper();
                File jsonFile = new File("src/main/python/data/courses.json");
                List<Course> courses = mapper.readValue(jsonFile, new TypeReference<List<Course>>() {});
                for (Course course : courses) {
                    courseDao.save(course);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCourses() {
        return courseDao.findAll(); //new
    }

}
