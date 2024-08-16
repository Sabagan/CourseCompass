package com.example.coursecompass.service;

import com.example.coursecompass.model.Mycourse;
import com.example.coursecompass.dao.MycourseDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MycourseServiceTest {

    @InjectMocks
    private MycourseService mycourseService;

    @Mock
    private MycourseDao mycourseDao;

    @Test
    void save_ShouldCallSaveOnMycourseServiceDao() {
        Mycourse mycourse = new Mycourse();
        mycourse.setUserId(1L);
        mycourse.setCourseProgram("Physical Sciences");
        mycourse.setCourseName("Astronomy");
        mycourse.setCourseCode("PCS 181");

        mycourseService.save(mycourse);

        verify(mycourseDao).save(mycourse);
    }

    @Test
    void getAllCourseNames_ShouldReturnCourseNames() {
        // Arrange
        List<String> courseNames = List.of("Course 1", "Course 2");
        when(mycourseDao.getAllCourseNames()).thenReturn(courseNames);

        // Act
        List<String> result = mycourseService.getAllCourseNames();

        // Assert
        assertEquals(courseNames, result);
        verify(mycourseDao, times(1)).getAllCourseNames();
    }

    @Test
    void findByUserId_ShouldReturnCoursesForUser() {
        Mycourse mycourse = new Mycourse();
        mycourse.setUserId(1L);
        mycourse.setCourseProgram("Physical Sciences");
        mycourse.setCourseName("Astronomy");
        mycourse.setCourseCode("PCS 181");

        // Arrange
        List<Mycourse> courses = new ArrayList<>();
        courses.add(mycourse);
        when(mycourseDao.findByUserId(1L)).thenReturn(courses);

        // Act
        List<Mycourse> result = mycourseService.findByUserId(1L);

        // Assert
        assertEquals(courses, result);
        verify(mycourseDao, times(1)).findByUserId(1L);
    }

    @Test
    void delete_ShouldCallDaoDelete() {
        // Act
        mycourseService.delete(1L, "101");

        // Assert
        verify(mycourseDao, times(1)).delete(1L, "101");
    }

    @Test
    void exists_ShouldReturnTrueWhenCourseExists() {
        // Arrange
        when(mycourseDao.exists(1L, "101")).thenReturn(true);

        // Act
        boolean result = mycourseService.exists(1L, "101");

        // Assert
        assertTrue(result);
        verify(mycourseDao, times(1)).exists(1L, "101");
    }

    @Test
    void exists_ShouldReturnFalseWhenCourseDoesNotExist() {
        // Arrange
        when(mycourseDao.exists(1L, "101")).thenReturn(false);

        // Act
        boolean result = mycourseService.exists(1L, "101");

        // Assert
        assertFalse(result);
        verify(mycourseDao, times(1)).exists(1L, "101");
    }
}
