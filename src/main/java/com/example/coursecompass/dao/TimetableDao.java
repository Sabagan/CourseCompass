package com.example.coursecompass.dao;

import com.example.coursecompass.model.Timetable;
import com.example.coursecompass.rowmapper.TimetableRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimetableDao {
    private final JdbcTemplate jdbcTemplate;

    public TimetableDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addCourseToTimetable(Timetable timetable) {
        String sql = "INSERT INTO timetable (user_id, year, semester, course_name) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, timetable.getUserId(), timetable.getYear(), timetable.getSemester(), timetable.getCourseName());
    }

    public void removeCourseFromTimetable(Long userId, String courseName, Integer year, String semester) {
        String sql = "DELETE FROM timetable WHERE user_id = ? AND course_name = ? AND year = ? AND semester = ?";
        jdbcTemplate.update(sql, userId, courseName, year, semester);
    }

    public void removeCourseFromTimetable(Long userId, String courseName) {
        String sql = "DELETE FROM timetable WHERE user_id = ? AND course_name = ?";
        jdbcTemplate.update(sql, userId, courseName);
    }

    public List<Timetable> findByUserId(Long userId) {
        String sql = "SELECT * FROM timetable WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new TimetableRowMapper());
    }
}