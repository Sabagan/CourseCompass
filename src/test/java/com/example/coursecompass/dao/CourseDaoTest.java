package com.example.coursecompass.dao;

import com.example.coursecompass.model.Course;
import com.example.coursecompass.rowmapper.CourseRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseDaoTest {

    @InjectMocks
    private CourseDao courseDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void count_ShouldReturnNumberOfCourses() {
        when(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM course", Integer.class)).thenReturn(5);

        int result = courseDao.count();

        assertEquals(5, result);
        verify(jdbcTemplate).queryForObject("SELECT COUNT(*) FROM course", Integer.class);
    }

    void findAll_ShouldReturnListOfCourses() {
        List<Course> mockCourses = List.of(
                new Course("CS", "101", "Intro to Programming", "Learn basic programming concepts."),
                new Course("CS", "102", "Data Structures", "Explore data structures.")
        );

        when(jdbcTemplate.query("SELECT * FROM course", new CourseRowMapper())).thenReturn(mockCourses);

        List<Course> result = courseDao.findAll();

        assertEquals(2, result.size());
        assertEquals("CS", result.get(0).getCourseProgram());
        assertEquals("101", result.get(0).getCourseCode());
        verify(jdbcTemplate).query("SELECT * FROM course", new CourseRowMapper());
    }

    @Test
    void save_ShouldInsertCourseIntoDatabase() {
        Course course = new Course("CS", "101", "Intro to Programming", "Learn basic programming concepts.");

        courseDao.save(course);

        verify(jdbcTemplate).update(
                "INSERT INTO course (course_program, course_code, course_name, course_description) VALUES (?, ?, ?, ?)",
                course.getCourseProgram(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCourseDescription()
        );
    }
}
