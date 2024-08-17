package com.example.coursecompass.controller;

import com.example.coursecompass.model.Mycourse;
import com.example.coursecompass.model.TimetableCourse;
import com.example.coursecompass.model.User;
import com.example.coursecompass.service.MycourseService;
import com.example.coursecompass.service.TimetableService;
import com.example.coursecompass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimetableControllerTest {

    @InjectMocks
    private TimetableController timetableController;

    @Mock
    private TimetableService timetableService;

    @Mock
    private UserService userService;

    @Mock
    private MycourseService mycourseService;

    @Mock
    private HttpSession session;

    @Test
    void saveTimetable_ShouldAddCoursetoTimetable() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setId(1L);

        TimetableCourse timetableCourse = new TimetableCourse();
        timetableCourse.setCourseName("testCourse");

        when(session.getAttribute("loggedInUser")).thenReturn(username);

        when(userService.findUserByUsername(username)).thenReturn(mockUser);

        timetableController.saveTimetable(timetableCourse, session);
        assertEquals(mockUser.getId(), timetableCourse.getUserId());

        verify(timetableService).addCourseToTimetable(timetableCourse);
    }

    @Test
    void getAvailableCourses_ShouldReturnAvailableCourses() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setId(1L);

        // Mock timetable courses for the user
        TimetableCourse timetableCourse1 = new TimetableCourse();
        timetableCourse1.setCourseName("testCourse1");
        timetableCourse1.setUserId(mockUser.getId());

        // Mock my courses for the user
        Mycourse mycourse1 = new Mycourse();
        mycourse1.setCourseName("testCourse1");
        mycourse1.setUserId(mockUser.getId());

        Mycourse mycourse2 = new Mycourse();
        mycourse2.setCourseName("testCourse2");
        mycourse2.setUserId(mockUser.getId());

        // Mock session and service methods
        when(session.getAttribute("loggedInUser")).thenReturn("testUser");
        when(userService.findUserByUsername("testUser")).thenReturn(mockUser);

        List<TimetableCourse> timetableCourses = List.of(timetableCourse1);
        when(timetableService.findByUserId(mockUser.getId())).thenReturn(timetableCourses);

        List<Mycourse> mycourses = List.of(mycourse1, mycourse2);
        when(mycourseService.findByUserId(mockUser.getId())).thenReturn(mycourses);

        // Act
        List<Mycourse> availableCourses = timetableController.getAvailableCourses(session);

        // Assert
        assertEquals(availableCourses.size(), 1);
        assertEquals(availableCourses.get(0).getCourseName(), mycourse2.getCourseName());
    }

    @Test
    void getAvailableCourses_ShouldReturnNoAvailableCourses() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setId(1L);

        // Mock timetable courses for the user
        TimetableCourse timetableCourse1 = new TimetableCourse();
        timetableCourse1.setCourseName("testCourse1");
        timetableCourse1.setUserId(mockUser.getId());

        TimetableCourse timetableCourse2 = new TimetableCourse();
        timetableCourse2.setCourseName("testCourse2");
        timetableCourse2.setUserId(mockUser.getId());

        // Mock my courses for the user
        Mycourse mycourse1 = new Mycourse();
        mycourse1.setCourseName("testCourse1");
        mycourse1.setUserId(mockUser.getId());

        Mycourse mycourse2 = new Mycourse();
        mycourse2.setCourseName("testCourse2");
        mycourse2.setUserId(mockUser.getId());

        // Mock session and service methods
        when(session.getAttribute("loggedInUser")).thenReturn("testUser");
        when(userService.findUserByUsername("testUser")).thenReturn(mockUser);

        List<TimetableCourse> timetableCourses = List.of(timetableCourse1, timetableCourse2);
        when(timetableService.findByUserId(mockUser.getId())).thenReturn(timetableCourses);

        List<Mycourse> mycourses = List.of(mycourse1, mycourse2);
        when(mycourseService.findByUserId(mockUser.getId())).thenReturn(mycourses);

        // Act
        List<Mycourse> availableCourses = timetableController.getAvailableCourses(session);

        // Assert
        assertEquals(availableCourses.size(), 0);
    }

    @Test
    void getTimetable_ShouldReturnTimetable_WhenNonEmpty() {
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setId(1L);

        TimetableCourse timetableCourse1 = new TimetableCourse();
        timetableCourse1.setCourseName("testCourse1");
        timetableCourse1.setUserId(mockUser.getId());

        TimetableCourse timetableCourse2 = new TimetableCourse();
        timetableCourse2.setCourseName("testCourse2");
        timetableCourse2.setUserId(2L);

        TimetableCourse timetableCourse3 = new TimetableCourse();
        timetableCourse3.setCourseName("testCourse2");
        timetableCourse3.setUserId(mockUser.getId());

        when(session.getAttribute("loggedInUser")).thenReturn("testUser");
        when(userService.findUserByUsername("testUser")).thenReturn(mockUser);
        when(timetableService.findByUserId(1l)).thenReturn(List.of(timetableCourse1, timetableCourse3));

        List<TimetableCourse> timetableCourses = timetableController.getTimetable(session);
        assertEquals(timetableCourses.size(), 2);

        verify(timetableService).findByUserId(1l);
    }

    @Test
    void getTimetable_ShouldReturnTimetable_WhenEmpty() {
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setId(1L);

        when(session.getAttribute("loggedInUser")).thenReturn("testUser");
        when(userService.findUserByUsername("testUser")).thenReturn(mockUser);
        when(timetableService.findByUserId(1l)).thenReturn(List.of());

        List<TimetableCourse> timetableCourses = timetableController.getTimetable(session);
        assertEquals(timetableCourses.size(), 0);

        verify(timetableService).findByUserId(1l);
    }

    @Test
    void removeCourseFromTimetable_ShouldRemoveCourseFromTimetable() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setId(1L);

        TimetableCourse timetableCourse1 = new TimetableCourse();
        timetableCourse1.setCourseName("testCourse1");
        timetableCourse1.setUserId(mockUser.getId());
        timetableCourse1.setSemester("Fall");
        timetableCourse1.setYear(1);

        when(session.getAttribute("loggedInUser")).thenReturn(username);
        when(userService.findUserByUsername("testUser")).thenReturn(mockUser);

        timetableController.removeCourseFromTimetable(timetableCourse1.getCourseName(), timetableCourse1.getYear(), timetableCourse1.getSemester(), session);

        verify(timetableService).removeCourseFromTimetable(timetableCourse1.getUserId(), timetableCourse1.getCourseName(), timetableCourse1.getYear(), timetableCourse1.getSemester());
    }
}
