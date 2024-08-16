package com.example.coursecompass.dao;


import com.example.coursecompass.model.Timetable;
import com.example.coursecompass.rowmapper.TimetableRowMapper;
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
public class TimetableDaoTest {

    @InjectMocks
    private TimetableDao timetableDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void addCourseToTimetable_ShouldInsertCourse() {
        Timetable timetable = new Timetable();
        timetable.setUserId(1L);
        timetable.setYear(1);
        timetable.setSemester("Fall");
        timetable.setCourseName("Math 101");

        timetableDao.addCourseToTimetable(timetable);

        verify(jdbcTemplate).update(
                "INSERT INTO timetable (user_id, year, semester, course_name) VALUES (?, ?, ?, ?)",
                timetable.getUserId(),
                timetable.getYear(),
                timetable.getSemester(),
                timetable.getCourseName()
        );
    }

    @Test
    void removeCourseFromTimetable_ByUserIdCourseNameYearSemester_ShouldDeleteCourse() {
        Long userId = 1L;
        String courseName = "Math 101";
        Integer year = 1;
        String semester = "Fall";

        timetableDao.removeCourseFromTimetable(userId, courseName, year, semester);

        verify(jdbcTemplate).update(
                "DELETE FROM timetable WHERE user_id = ? AND course_name = ? AND year = ? AND semester = ?",
                userId,
                courseName,
                year,
                semester
        );
    }

    @Test
    void removeCourseFromTimetable_ByUserIdAndCourseName_ShouldDeleteCourse() {
        Long userId = 1L;
        String courseName = "Math 101";

        timetableDao.removeCourseFromTimetable(userId, courseName);

        verify(jdbcTemplate).update(
                "DELETE FROM timetable WHERE user_id = ? AND course_name = ?",
                userId,
                courseName
        );
    }

    @Test
    void findByUserId_ShouldReturnListOfTimetable_WhenUserIdExists() {
        Long userId = 1L;
        List<Timetable> mockTimetables = new ArrayList<>();
        Timetable timetable1 = new Timetable();
        timetable1.setUserId(userId);
        timetable1.setYear(1);
        timetable1.setSemester("Fall");
        timetable1.setCourseName("Math 101");
        mockTimetables.add(timetable1);

        when(jdbcTemplate.query(
                eq("SELECT * FROM timetable WHERE user_id = ?"),
                any(Object[].class),
                any(TimetableRowMapper.class)
        )).thenReturn(mockTimetables);

        List<Timetable> result = timetableDao.findByUserId(userId);

        assertEquals(1, result.size());
        assertEquals("Math 101", result.get(0).getCourseName());
        verify(jdbcTemplate).query(
                eq("SELECT * FROM timetable WHERE user_id = ?"),
                any(Object[].class),
                any(TimetableRowMapper.class)
        );
    }
}
