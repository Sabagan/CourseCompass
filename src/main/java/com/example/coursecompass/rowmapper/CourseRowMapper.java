package com.example.coursecompass.rowmapper;

import com.example.coursecompass.model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("id"));
        course.setCourseProgram(rs.getString("course_program"));
        course.setCourseCode(rs.getString("course_code"));
        course.setCourseName(rs.getString("course_name"));
        course.setCourseDescription(rs.getString("course_description"));
        return course;
    }
}