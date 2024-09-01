package com.example.coursecompass.controller;

import com.example.coursecompass.model.Mycourse;
import com.example.coursecompass.model.TimetableCourse;
import com.example.coursecompass.model.User;
import com.example.coursecompass.service.MycourseService;
import com.example.coursecompass.service.TimetableCourseService;
import com.example.coursecompass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MycourseControllerTest {

    @InjectMocks
    private MycourseController mycourseController;

    @Mock
    private MycourseService mycourseService;

    @Mock
    private UserService userService;

    @Mock
    private TimetableCourseService timetableCourseService;

    @Mock
    private HttpSession session;

//    @Test
//    void getAllCourseNames_WhenEmpty() {
//        when(mycourseService.getAllCourseNames()).thenReturn(new ArrayList<>());
//
//        List<String> names = mycourseController.getAllCourseNames();
//        assertEquals(names.size(), 0);
//    }
//
//    @Test
//    void getAllCourseNames_WhenNotEmpty() {
//        when(mycourseService.getAllCourseNames()).thenReturn(List.of("App Development", "Cyber Security"));
//
//        List<String> names = mycourseController.getAllCourseNames();
//        assertEquals(names.size(), 2);
//    }

    @Test
    void addCourse_ShouldAddCourse() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setId(1L);

        Mycourse mockCourse = new Mycourse();
        mockCourse.setUserId(1L);
        mockCourse.setCourseProgram("Faculty of Science");
        mockCourse.setCourseCode("101");
        mockCourse.setCourseName("Computer Science");
        mockCourse.setCourseDescription("Learn Python");

        when(session.getAttribute("loggedInUser")).thenReturn(username);
        when(userService.findUserByUsername(username)).thenReturn(mockUser);
        when(mycourseService.exists(1L, "101")).thenReturn(true);

        assertEquals(ResponseEntity.ok("Course added"), mycourseController.addCourse(mockCourse.getCourseProgram(), mockCourse.getCourseCode(), mockCourse.getCourseName(), mockCourse.getCourseDescription(), session));
    }

    @Test
    void deleteCourse_ShouldDeleteCourse() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setId(1L);

        Mycourse mockCourse = new Mycourse();
        mockCourse.setCourseCode("CPS 571");
        mockCourse.setCourseName("Cyber Security");
        mockCourse.setUserId(1L);

        TimetableCourse mockTimetableCourse = new TimetableCourse();
        mockTimetableCourse.setUserId(1L);
        mockTimetableCourse.setCourseName("Cyber Security");

        when(session.getAttribute("loggedInUser")).thenReturn(username);

        when(userService.findUserByUsername(username)).thenReturn(mockUser);

        assertEquals(ResponseEntity.ok("Course deleted"), mycourseController.deleteCourse(mockCourse.getCourseCode(), mockCourse.getCourseName(), session));
    }
}
