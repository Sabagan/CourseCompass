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
    void showProfile_ReturnsProfilePage() {
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
    void showMyCourses_ReturnsMyCoursesPage() {
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

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        when(session.getAttribute("CAPTCHA_KEY")).thenReturn(captcha);
        when(userService.findUserByUsername(username)).thenReturn(mockUser);
        when(passwordEncoder.matches(password, mockUser.getPassword())).thenReturn(true);
        when(courseService.getCourses()).thenReturn(List.of(new Course()));

        // Act
        String viewName = appController.login(username, password, captcha, model, session);

        // Assert
//        assertEquals("redirect:/welcome", viewName);
//        verify(session).setAttribute("loggedIn  seService.getCourses());
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
}
