package com.example.coursecompass.service;

import com.example.coursecompass.dao.TimetableCourseDao;
import com.example.coursecompass.model.TimetableCourse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TimetableCourseServiceTest {

    @InjectMocks
    private TimetableCourseService timetableCourseService;

    @Mock
    private TimetableCourseDao timetableCourseDao;

    @Test
    void addCourseToTimetable_ShouldCallAddCourseToTimetableDao() {
        TimetableCourse timetableCourse = new TimetableCourse();
        timetableCourse.setCourseName("Computer Science II");
        timetableCourse.setYear(1);
        timetableCourse.setSemester("Winter");

        timetableCourseService.addCourseToTimetable(timetableCourse);

        verify(timetableCourseDao).addCourseToTimetable(timetableCourse);
    }

//    @Test
//    void removeCourseFromTimetable_ShouldCallRemoveCourseFromTimetableDao() {
//        timetableCourseService.removeCourseFromTimetable(1L, "Computer Science I", 1, "Fall");
//
//        verify(timetableCourseDao).removeCourseFromTimetable(1L, "Computer Science I", 1, "Fall");
//    }

    @Test
    void removeCourseFromTimetable_WhenYearSemesterUnknown_ShouldCallRemoveCourseFromTimetableDao() {
        timetableCourseService.removeCourseFromTimetable(1L, "Computer Science I");

        verify(timetableCourseDao).removeCourseFromTimetable(1L, "Computer Science I");
    }

    @Test
    void findByUserId_ShouldReturnTimetableCourses_WhenUserIdIsFound() {
        TimetableCourse timetableCourse = new TimetableCourse();
        timetableCourse.setUserId(1L);

        timetableCourseService.findByUserId(timetableCourse.getUserId());

        verify(timetableCourseDao).findByUserId(1L);
    }
}
