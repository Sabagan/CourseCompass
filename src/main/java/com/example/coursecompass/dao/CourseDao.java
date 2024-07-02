package com.example.coursecompass.dao;

import com.example.coursecompass.model.Course;
import com.example.coursecompass.rowmapper.CourseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int count() {
        String sql = "SELECT COUNT(*) FROM course";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Course> findAll() {
        String sql = "SELECT * FROM course";
        return jdbcTemplate.query(sql, new CourseRowMapper());
    }

    public void save(Course course) {
        String sql = "INSERT INTO course (course_program, course_code, course_name, course_description) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, course.getCourseProgram(), course.getCourseCode(), course.getCourseName(), course.getCourseDescription());
    }
}