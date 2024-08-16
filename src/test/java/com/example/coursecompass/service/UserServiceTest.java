package com.example.coursecompass.service;

import com.example.coursecompass.dao.UserDao;
import com.example.coursecompass.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Test
    void saveUser_ShouldCallSaveOnUserDao() {
        User user = new User();
        user.setUsername("testuser");

        userService.saveUser(user);

        verify(userDao).save(user);
    }

    @Test
    void findUserByUsername_ShouldReturnUser_WhenUserExists() {
        String username = "testuser";
        User mockUser = new User();
        mockUser.setUsername(username);

        when(userDao.findByUsername(username)).thenReturn(mockUser);

        User result = userService.findUserByUsername(username);

        assertEquals(username, result.getUsername());
        verify(userDao).findByUsername(username);
    }
}
