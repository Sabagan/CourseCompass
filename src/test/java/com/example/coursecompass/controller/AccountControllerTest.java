package com.example.coursecompass.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.coursecompass.model.User;
import com.example.coursecompass.service.UserService;

import jakarta.servlet.http.HttpSession;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    /*
     * Test for getUserAccount method when user is logged in
     * Returns the correct user and `UserService` is called with the correct username
     */
    @Test
    void getUserAccount_ShouldReturnUser_WhenLoggedInUserExists() {
        String username = "testuser";
        User mockUser = new User();
        mockUser.setUsername(username);

        // Mock the session to return a username when `getAttribute("loggedInUser")` is called
        when(session.getAttribute("loggedInUser")).thenReturn(username);

        // Mock the `UserService` to return a user object when `findUserByUsername` is called
        when(userService.findUserByUsername(username)).thenReturn(mockUser);

        // Call `getUserAccount` and assert that the returned user's username matches the expected value
        User result = accountController.getUserAccount(session);
        assertEquals(username, result.getUsername());
        verify(userService).findUserByUsername(username);
    }

    /*
     * Test for getUserAccount method when no user is logged in
     * Returns `null` and `UserService` is never called
     */
    @Test
    void getUserAccount_ShouldReturnNull_WhenLoggedInUserDoesNotExist() {
        // Mock the session to return `null` when `getAttribute("loggedInUser")` is called
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        // Call `getUserAccount` and assert that the result is `null`
        User result = accountController.getUserAccount(session);
        assertNull(result);
        verify(userService, never()).findUserByUsername(anyString());
    }

    /*
     * Test for updateAccount method when user is successfully updated
     * Returns a success response and `UserService.saveUser` is called with the updated user
     */
    @Test
    void updateAccount_ShouldReturnSuccessTrue_WhenUserIsUpdated() {
        String username = "testuser";
        User currentUser = new User();
        currentUser.setUsername(username);
        User updatedUser = new User();
        updatedUser.setName("Updated Name");

        // Mock the session to return a username when getAttribute("loggedInUser") is called
        when(session.getAttribute("loggedInUser")).thenReturn(username);

        // Mock the UserService to return the current user when findUserByUsername is called
        when(userService.findUserByUsername(username)).thenReturn(currentUser);

        // Call updateAccount with the updated user and assert that the response contains success: true
        Map<String, Object> response = accountController.updateAccount(updatedUser, session);
        assertTrue((Boolean) response.get("success"));

        // Verify that saveUser is called with the correct user and that the user’s name is updated
        verify(userService).saveUser(currentUser);
        assertEquals("Updated Name", currentUser.getName());
    }

    /*
     * Test for updateAccount method when user is not found
     * Returns a failure response and `UserService.saveUser` is not called
     */
    @Test
    void updateAccount_ShouldReturnSuccessFalse_WhenUserNotFound() {
        String username = "testuser";
        User updatedUser = new User();

        // Mock the session to return a username when `getAttribute("loggedInUser")` is called
        when(session.getAttribute("loggedInUser")).thenReturn(username);

        // Mock the `UserService` to return `null` when `findUserByUsername` is called
        when(userService.findUserByUsername(username)).thenReturn(null);

        // Call `updateAccount` with the updated user and assert that the response contains `success: false`
        Map<String, Object> response = accountController.updateAccount(updatedUser, session);
        assertFalse((Boolean) response.get("success"));

        // Verify that `saveUser` is never called
        verify(userService, never()).saveUser(any(User.class));
    }
}
