package com.example.coursecompass.dao;


import com.example.coursecompass.model.TimetableCourse;
import com.example.coursecompass.rowmapper.TimetableCourseRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TimetableCourseDaoTest {

    @InjectMocks
    private TimetableCourseDao timetableCourseDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void addCourseToTimetable_ShouldInsertCourse() {
        TimetableCourse timetableCourse = new TimetableCourse();
        timetableCourse.setUserId(1L);
        timetableCourse.setTimetableId(1);
        timetableCourse.setYear(1);
        timetableCourse.setSemester("Fall");
        timetableCourse.setCourseName("Math 101");

        timetableCourseDao.addCourseToTimetable(timetableCourse);

        verify(jdbcTemplate).update(
                "INSERT INTO timetable (user_id, timetable_id, year, semester, course_name) VALUES (?, ?, ?, ?, ?)",
                timetableCourse.getUserId(),
                timetableCourse.getTimetableId(),
                timetableCourse.getYear(),
                timetableCourse.getSemester(),
                timetableCourse.getCourseName()
        );
    }

    @Test
    void removeCourseFromTimetable_ByUserIdCourseNameYearSemester_ShouldDeleteCourse() {
        Long userId = 1L;
        String courseName = "Math 101";
        Integer year = 1;
        String semester = "Fall";
        Integer timetableId = 1;

        timetableCourseDao.removeCourseFromTimetable(userId, timetableId, courseName, year, semester);

        verify(jdbcTemplate).update(
                "DELETE FROM timetable WHERE user_id = ? AND timetable_id = ? AND course_name = ? AND year = ? AND semester = ?",
                userId,
                timetableId,
                courseName,
                year,
                semester
        );
    }

    @Test
    void removeCourseFromTimetable_ByUserIdAndCourseName_ShouldDeleteCourse() {
        Long userId = 1L;
        String courseName = "Math 101";

        timetableCourseDao.removeCourseFromTimetable(userId, courseName);

        verify(jdbcTemplate).update(
                "DELETE FROM timetable WHERE user_id = ? AND course_name = ?",
                userId,
                courseName
        );
    }

    @Test
    void findByUserId_ShouldReturnListOfTimetable_WhenUserIdExists() {
        Long userId = 1L;
        List<TimetableCourse> mockTimetableCourses = new ArrayList<>();
        TimetableCourse timetableCourse1 = new TimetableCourse();
        timetableCourse1.setUserId(userId);
        timetableCourse1.setYear(1);
        timetableCourse1.setSemester("Fall");
        timetableCourse1.setCourseName("Math 101");
        mockTimetableCourses.add(timetableCourse1);

        when(jdbcTemplate.query(
                eq("SELECT * FROM timetable WHERE user_id = ?"),
                any(Object[].class),
                any(TimetableCourseRowMapper.class)
        )).thenReturn(mockTimetableCourses);

        List<TimetableCourse> result = timetableCourseDao.findByUserId(userId);

        assertEquals(1, result.size());
        assertEquals("Math 101", result.get(0).getCourseName());
        verify(jdbcTemplate).query(
                eq("SELECT * FROM timetable WHERE user_id = ?"),
                any(Object[].class),
                any(TimetableCourseRowMapper.class)
        );
    }

    @Test
    void findByUserTimetableId_ShouldReturnListOfTimetable_WhenUserIdExists() {
        Long userId = 1L;
        List<TimetableCourse> mockTimetableCourses = new ArrayList<>();
        TimetableCourse timetableCourse1 = new TimetableCourse();
        timetableCourse1.setUserId(userId);
        timetableCourse1.setTimetableId(1);
        timetableCourse1.setYear(1);
        timetableCourse1.setSemester("Fall");
        timetableCourse1.setCourseName("Math 101");
        mockTimetableCourses.add(timetableCourse1);

        when(jdbcTemplate.query(
                eq("SELECT * FROM timetable WHERE user_id = ? AND timetable_id = ?"),
                any(Object[].class),
                any(TimetableCourseRowMapper.class)
        )).thenReturn(mockTimetableCourses);

        List<TimetableCourse> result = timetableCourseDao.findByUserTimetableId(userId, 1);

        assertEquals(1, result.size());
        assertEquals("Math 101", result.get(0).getCourseName());
        verify(jdbcTemplate).query(
                eq("SELECT * FROM timetable WHERE user_id = ? AND timetable_id = ?"),
                any(Object[].class),
                any(TimetableCourseRowMapper.class)
        );
    }
}
