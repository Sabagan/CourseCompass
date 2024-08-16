package com.example.coursecompass.service;

import com.example.coursecompass.dao.CourseDao;
import com.example.coursecompass.model.Course;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseDao courseDao;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void init_ShouldLoadCourses_WhenDatabaseIsEmpty() throws Exception {
        when(courseDao.count()).thenReturn(0);

        // Run the init() method
        courseService.init();

        // Verify that save() was called on each course in the list
        verify(courseDao, times(3577)).save(any(Course.class));
    }

    @Test
    void getCourses_ShouldReturnAllCourses() {
        // Prepare the mock data
        List<Course> mockCourses = new ArrayList<>();
        mockCourses.add(new Course("CS", "101", "Introduction to Computer Science", "Basics of CS"));

        when(courseDao.findAll()).thenReturn(mockCourses);

        // Call the method under test
        List<Course> courses = courseService.getCourses();

        // Verify that the method returns the correct list of courses
        assertEquals(mockCourses, courses);
        verify(courseDao).findAll();
    }
}
