package com.example.coursecompass.service;

import com.example.coursecompass.dao.TimetableCourseDao;
import com.example.coursecompass.model.TimetableCourse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableCourseService {
    private final TimetableCourseDao timetableCourseDao;

    public TimetableCourseService(TimetableCourseDao timetableCourseDao) {
        this.timetableCourseDao = timetableCourseDao;
    }

    public void addCourseToTimetable(TimetableCourse timetableCourse) {
        timetableCourseDao.addCourseToTimetable(timetableCourse);
    }

    public void removeCourseFromTimetable(Long userId, Integer timetableId, String courseName, Integer year, String semester) {
        timetableCourseDao.removeCourseFromTimetable(userId, timetableId, courseName, year, semester);
    }

    public void removeCourseFromTimetable(Long userId, String courseName) {
        timetableCourseDao.removeCourseFromTimetable(userId, courseName);
    }

    public void removeCourseFromTimetable(Long userId, Integer timetableId) {
        timetableCourseDao.removeCourseFromTimetable(userId, timetableId);
    }

    public List<TimetableCourse> findByUserId(Long userId) {
        return timetableCourseDao.findByUserId(userId);
    }

    public  List<TimetableCourse> findByUserTimetableId(Long userId, Integer timetableId) {
        return timetableCourseDao.findByUserTimetableId(userId, timetableId);
    }
}