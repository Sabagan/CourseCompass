package com.example.coursecompass.service;

import com.example.coursecompass.dao.TimetableDao;
import com.example.coursecompass.model.Timetable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TimetableServiceTest {

    @InjectMocks
    private TimetableService timetableService;

    @Mock
    private TimetableDao timetableDao;

    @Test
    void addCourseToTimetable_ShouldCallAddCourseToTimetableDao() {
        Timetable timetable = new Timetable();
        timetable.setCourseName("Computer Science II");
        timetable.setYear(1);
        timetable.setSemester("Winter");

        timetableService.addCourseToTimetable(timetable);

        verify(timetableDao).addCourseToTimetable(timetable);
    }

    @Test
    void removeCourseFromTimetable_ShouldCallRemoveCourseFromTimetableDao() {
        timetableService.removeCourseFromTimetable(1L, "Computer Science I", 1, "Fall");

        verify(timetableDao).removeCourseFromTimetable(1L, "Computer Science I", 1, "Fall");
    }

    @Test
    void removeCourseFromTimetable_WhenYearSemesterUnknown_ShouldCallRemoveCourseFromTimetableDao() {
        timetableService.removeCourseFromTimetable(1L, "Computer Science I");

        verify(timetableDao).removeCourseFromTimetable(1L, "Computer Science I");
    }

    @Test
    void findByUserId_ShouldReturnTimetableCourses_WhenUserIdIsFound() {
        Timetable timetable = new Timetable();
        timetable.setUserId(1L);

        timetableService.findByUserId(timetable.getUserId());

        verify(timetableDao).findByUserId(1L);
    }
}
