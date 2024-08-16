package com.example.coursecompass.dao;

import com.example.coursecompass.model.User;
import com.example.coursecompass.rowmapper.UserRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Annotation used to enable Mockito support in JUnit5 tests, allows use of @Mock and @InjectMocks
public class UserDaoTest {

    /*
     * Creates an instance of UserDao
     * Injects the mocked JdbcTemplate into it
     * Isolation testing of UserDao -> Mock the database interactions
     */
    @InjectMocks
    private UserDao userDao;

    /*
     * Creates a mock of JdbcTemplate -> Simulates database operations
     * Doesn't interact with real database during unit tests
     */
    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {
        // Define a test username and create a mock User object to simulate the user retrieved from the database
        String username = "testuser";
        User mockUser = new User();
        mockUser.setUsername(username);

        /*
         * Sets up the behavior of the mock JdbcTemplate
         * When the queryForObject is called with the special SQL query, an array of arguments, and any
           UserRowMapper, it should return mockUser
         */
        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM user WHERE username = ?"),
                any(Object[].class),
                any(UserRowMapper.class)
        )).thenReturn(mockUser);

        // The method call being tested -> should return the mockUser
        User result = userDao.findByUsername(username);

        // Assertion verifies that the username of the returned user matches the expected value
        assertEquals(username, result.getUsername());

        // Checks that queryForObject was indeed called with the correct SQL query, arguments and row mapper
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM user WHERE username = ?"),
                any(Object[].class),
                any(UserRowMapper.class)
        );
    }

    @Test
    void findByUsername_ShouldReturnNull_WhenUserDoesNotExist() {
        String username = "nonexistent";

        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM user WHERE username = ?"),
                any(Object[].class),
                any(UserRowMapper.class)
        )).thenThrow(new EmptyResultDataAccessException(1));

        User result = userDao.findByUsername(username);

        assertNull(result);
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM user WHERE username = ?"),
                any(Object[].class),
                any(UserRowMapper.class)
        );
    }

    @Test
    void save_ShouldInsertUser_WhenUserHasNoId() {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");
        user.setName("New User");
        user.setDob(new Date());
        user.setEmail("newuser@example.com");

        userDao.save(user);

        verify(jdbcTemplate).update(
                eq("INSERT INTO user (username, password, name, dob, email) VALUES (?, ?, ?, ?, ?)"),
                eq(user.getUsername()),
                eq(user.getPassword()),
                eq(user.getName()),
                eq(user.getDob()),
                eq(user.getEmail())
        );
    }

    @Test
    void save_ShouldUpdateUser_WhenUserHasId() {
        User user = new User();
        user.setId(1L); // Simulate existing user
        user.setUsername("existinguser");
        user.setPassword("newpassword");
        user.setName("Updated User");
        user.setDob(new Date());
        user.setEmail("updateduser@example.com");

        userDao.save(user);

        verify(jdbcTemplate).update(
                eq("UPDATE user SET password = ?, name = ?, dob = ?, email = ? WHERE username = ?"),
                eq(user.getPassword()),
                eq(user.getName()),
                eq(user.getDob()),
                eq(user.getEmail()),
                eq(user.getUsername())
        );
    }
}