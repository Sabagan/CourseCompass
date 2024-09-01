package com.example.coursecompass.dao;

import com.example.coursecompass.model.TimetableCourse;
import com.example.coursecompass.rowmapper.TimetableCourseRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimetableCourseDao {

    private final JdbcTemplate jdbcTemplate;

    public TimetableCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addCourseToTimetable(TimetableCourse timetableCourse) {
        String sql = "INSERT INTO timetable (user_id, timetable_id, year, semester, course_name) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, timetableCourse.getUserId(), timetableCourse.getTimetableId(), timetableCourse.getYear(), timetableCourse.getSemester(), timetableCourse.getCourseName());
    }

    public void removeCourseFromTimetable(Long userId, Integer timetableId, String courseName, Integer year, String semester) {
        String sql = "DELETE FROM timetable WHERE user_id = ? AND timetable_id = ? AND course_name = ? AND year = ? AND semester = ?";
        jdbcTemplate.update(sql, userId, timetableId, courseName, year, semester);
    }

    public void removeCourseFromTimetable(Long userId, String courseName) {
        String sql = "DELETE FROM timetable WHERE user_id = ? AND course_name = ?";
        jdbcTemplate.update(sql, userId, courseName);
    }

    public List<TimetableCourse> findByUserId(Long userId) {
        String sql = "SELECT * FROM timetable WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new TimetableCourseRowMapper());
    }

    public List<TimetableCourse> findByUserTimetableId(Long userId, Integer timetableId) {
        String sql = "SELECT * FROM timetable WHERE user_id = ? AND timetable_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId, timetableId}, new TimetableCourseRowMapper());
    }
}