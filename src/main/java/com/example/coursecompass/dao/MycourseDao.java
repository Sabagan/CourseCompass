package com.example.coursecompass.dao;
import com.example.coursecompass.rowmapper.MycourseRowMapper;
import com.example.coursecompass.model.Mycourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MycourseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Mycourse mycourse) {
        String sql = "INSERT INTO mycourses (course_program, course_code, course_name, course_description) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, mycourse.getCourseProgram(), mycourse.getCourseCode(),
                mycourse.getCourseName(), mycourse.getCourseDescription());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM mycourses WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Mycourse findById(Long id) {
        String sql = "SELECT * FROM mycourses WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new MycourseRowMapper());
    }

    public List<Mycourse> findAll() {
        String sql = "SELECT * FROM mycourses";
        return jdbcTemplate.query(sql, new MycourseRowMapper());
    }

    public boolean exists(String courseCode) {
        String sql = "SELECT COUNT(*) FROM mycourses WHERE course_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, courseCode);
        return count != null && count > 0;
    }
}
