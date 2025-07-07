package com.example.coursecompass.controller;

import com.example.coursecompass.model.Course;
import com.example.coursecompass.model.User;
import com.example.coursecompass.service.CourseService;
import com.example.coursecompass.service.MycourseService;
import com.example.coursecompass.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppControllerTest {

    @InjectMocks
    private AppController appController;

    @Mock
    private UserService userService;

    @Mock
    private CourseService courseService;

    @Mock
    private MycourseService mycourseService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void home_ReturnsOnEntry() {
        assertEquals(appController.home(), "home");
    }

    @Test
    void showPlan_WhenLoggedIn() {
        String username = "testUser";

        when(session.getAttribute("loggedInUser")).thenReturn(username);

        assertEquals(appController.showPlan(session), "plan");
    }

    @Test
    void showPlan_WhenNotLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        assertEquals(appController.showPlan(session), "redirect:/login");
    }

    @Test
    void showWelcome_WhenLoggedIn() {
        String username = "testUser";

        when(session.getAttribute("loggedInUser")).thenReturn(username);

        assertEquals(appController.showWelcome(session), "welcome");
    }

    @Test
    void showWelcome_WhenNotLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        assertEquals(appController.showWelcome(session), "redirect:/login");
    }

    @Test
    void showAccount_WhenLoggedIn() {
        String username = "testUser";

        when(session.getAttribute("loggedInUser")).thenReturn(username);

        assertEquals(appController.showAccount(session), "account");
    }

    @Test
    void showAccount_WhenNotLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        assertEquals(appController.showAccount(session), "redirect:/login");
    }

    @Test
    void showRecommendations_WhenLoggedIn() {
        String username = "testUser";

        when(session.getAttribute("loggedInUser")).thenReturn(username);

        assertEquals(appController.showRecommendations(session), "recommendations");
    }

    @Test
    void showRecommendations_WhenNotLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        assertEquals(appController.showRecommendations(session), "redirect:/login");
    }

    @Test
    void showProfile_WhenLoggedIn() {
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setId(1L);

        when(session.getAttribute("loggedInUser")).thenReturn(mockUser.getUsername());
        when(userService.findUserByUsername(mockUser.getUsername())).thenReturn(mockUser);
        when(courseService.getCourses()).thenReturn(List.of());

        assertEquals(appController.showProfile(model, session), "profile");
        verify(model).addAttribute("courses", List.of());
    }

    @Test
    void showProfile_WhenNotLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        assertEquals(appController.showProfile(model, session), "redirect:/login");
    }

    @Test
    void showMyCourses_WhenLoggedIn() {
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setId(1L);

        when(session.getAttribute("loggedInUser")).thenReturn(mockUser.getUsername());
        when(userService.findUserByUsername(mockUser.getUsername())).thenReturn(mockUser);
        when(mycourseService.findByUserId(mockUser.getId())).thenReturn(List.of());

        assertEquals(appController.showMyCourses(model, session), "mycourses");
        verify(model).addAttribute("courses", List.of());
    }

    @Test
    void showMyCourses_WhenNotLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        assertEquals(appController.showMyCourses(model, session), "redirect:/login");
    }

    void register_ShouldRegisterUser() {
        String username = "test";
        String password = "test";
        String email = "test@test.com";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        assertEquals(appController.register(username, password, email), "redirect:/login");
    }

    @Test
    void loginForm_ShouldReturnLoginViewAndSetCaptchaAttributes() {
        // Arrange
        String captchaText = "testCaptcha";
        when(userService.generateCaptchaText()).thenReturn(captchaText);
        when(userService.createCaptchaImage(captchaText)).thenReturn("captchaImage");

        // Act
        String viewName = appController.loginForm(session, model);

        // Assert
        assertEquals("login", viewName);
        verify(session).setAttribute("CAPTCHA_KEY", captchaText);
        verify(model).addAttribute("captchaImage", "captchaImage");
    }

    @Test
    void login_ShouldReturnRedirectToWelcome_WhenCredentialsAndCaptchaAreCorrect() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String captcha = "testCaptcha";

        // Create a mock user with an encoded password
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword("$2a$10$6yuEKxAqesf2Ms5grDHlheUSksVml8CjLWbWx8/qo5sfzA7MxInxG"); // Simulating an encoded password

        // Mock session, service calls, and password matching
        when(session.getAttribute("CAPTCHA_KEY")).thenReturn(captcha);

        when(userService.findUserByUsername(username)).thenReturn(mockUser);

        when(courseService.getCourses()).thenReturn(List.of(new Course())); // Mock course list

        // Act
        String viewName = appController.login(username, password, captcha, model, session);

        // Assert
        assertEquals("redirect:/welcome", viewName); // Expecting a redirect to /welcome
        verify(session).setAttribute("loggedInUser", username);
        verify(model).addAttribute("username", mockUser.getUsername());
        verify(model).addAttribute("courses", courseService.getCourses());
    }

    @Test
    void login_ShouldReturnRedirectToLogin_WhenCredentialsAreIncorrect() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String captcha = "testCaptcha";

        when(session.getAttribute("CAPTCHA_KEY")).thenReturn(captcha);

        when(userService.findUserByUsername(username)).thenReturn(null);

        // Act
        String viewName = appController.login(username, password, captcha, model, session);

        // Assert
        assertEquals("redirect:/login", viewName);
        verify(model).addAttribute("message", "Invalid username or password");
    }

    @Test
    void login_ShouldReturnLoginViewWithCaptchaError_WhenCaptchaIsIncorrect() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String captcha = "wrongCaptcha";

        when(session.getAttribute("CAPTCHA_KEY")).thenReturn("testCaptcha");

        // Act
        String viewName = appController.login(username, password, captcha, model, session);

        // Assert
        assertEquals("login", viewName);
        verify(model).addAttribute("message", "CAPTCHA verification failed!");
    }

    @Test
    void logout_ShouldInvalidateSession_WhileRedirecting() {
        assertEquals(appController.logout(session), "redirect:/");
        verify(session).invalidate();
    }
}
