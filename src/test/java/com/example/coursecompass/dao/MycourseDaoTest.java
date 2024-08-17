package com.example.coursecompass.dao;

import com.example.coursecompass.model.Mycourse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MycourseDaoTest {

    @InjectMocks
    private MycourseDao mycourseDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void save_ShouldInsertMycourseIntoDatabase() {
        Mycourse mycourse = new Mycourse();
        mycourse.setUserId(1L);
        mycourse.setCourseCode("101");
        mycourse.setCourseName("Intro to Programming");
        mycourse.setCourseProgram("CS");
        mycourse.setCourseDescription("Learn basic programming concepts.");

        mycourseDao.save(mycourse);

        verify(jdbcTemplate).update(
                "INSERT INTO mycourses (user_id, course_program, course_code, course_name, course_description) VALUES (?, ?, ?, ?, ?)",
                mycourse.getUserId(),
                mycourse.getCourseProgram(),
                mycourse.getCourseCode(),
                mycourse.getCourseName(),
                mycourse.getCourseDescription()
        );
    }

    @Test
    void delete_ShouldRemoveMycourseFromDatabase() {
        Long userId = 1L;
        String courseCode = "CS101";

        mycourseDao.delete(userId, courseCode);

        verify(jdbcTemplate).update(
                "DELETE FROM mycourses WHERE user_id = ? AND course_code = ?",
                userId,
                courseCode
        );
    }

    @Test
    void findByUserId_ShouldReturnListOfMycourses() {
        Long userId = 1L;
        Mycourse mycourse1 = new Mycourse();
        mycourse1.setUserId(userId);
        mycourse1.setCourseProgram("Computer Science");
        mycourse1.setCourseCode("CS101");
        mycourse1.setCourseName("Intro to Programming");
        mycourse1.setCourseDescription("Learn the basics of programming.");

        Mycourse mycourse2 = new Mycourse();
        mycourse2.setUserId(userId);
        mycourse2.setCourseProgram("Computer Science");
        mycourse2.setCourseCode("CS102");
        mycourse2.setCourseName("Data Structures");
        mycourse2.setCourseDescription("Understand the fundamentals of data structures.");

        List<Mycourse> expectedCourses = Arrays.asList(mycourse1, mycourse2);

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(expectedCourses);

        List<Mycourse> actualCourses = mycourseDao.findByUserId(userId);

        assertEquals(expectedCourses, actualCourses);
        verify(jdbcTemplate, times(1)).query(eq("SELECT * FROM mycourses WHERE user_id = ?"), eq(new Object[]{userId}), any(RowMapper.class));
    }

    @Test
    void getAllCourseNames_ShouldReturnListOfCourseNames() {
        List<String> mockCourseNames = List.of("Intro to Programming", "Data Structures");

        when(jdbcTemplate.queryForList("SELECT course_name FROM mycourses", String.class)).thenReturn(mockCourseNames);

        List<String> result = mycourseDao.getAllCourseNames();

        assertEquals(2, result.size());
        assertEquals("Intro to Programming", result.get(0));
        verify(jdbcTemplate).queryForList("SELECT course_name FROM mycourses", String.class);
    }

    @Test
    void exists_ShouldReturnTrue_WhenCourseExists() {
        Long userId = 1L;
        String courseCode = "CS101";

        when(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM mycourses WHERE user_id = ? AND course_code = ?",
                new Object[]{userId, courseCode},
                Integer.class
        )).thenReturn(1);

        boolean result = mycourseDao.exists(userId, courseCode);

        assertTrue(result);
        verify(jdbcTemplate).queryForObject(
                "SELECT COUNT(*) FROM mycourses WHERE user_id = ? AND course_code = ?",
                new Object[]{userId, courseCode},
                Integer.class
        );
    }
}