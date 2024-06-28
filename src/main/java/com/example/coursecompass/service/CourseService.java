package com.example.coursecompass.service;

import com.example.coursecompass.model.Course;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class CourseService {
    private List<Course> courses;

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File("src/main/python/data/courses.json");
            courses = mapper.readValue(jsonFile, new TypeReference<List<Course>>() {});
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCourses() {
        return courses;
    }
}
