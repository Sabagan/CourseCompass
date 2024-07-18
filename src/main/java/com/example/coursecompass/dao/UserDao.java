package com.example.coursecompass.dao;

import com.example.coursecompass.model.Course;
import com.example.coursecompass.model.User;
import com.example.coursecompass.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, new UserRowMapper());
    }

    public void save(User user) {
        if (user.getId() == null) {
            String sql = "INSERT INTO user (username, password, name, dob, email) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getDob(), user.getEmail());
        } else {
            String sql = "UPDATE user SET password = ?, name = ?, dob = ?, email = ? WHERE username = ?";
            jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getDob(), user.getEmail(), user.getUsername());
        }
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

}