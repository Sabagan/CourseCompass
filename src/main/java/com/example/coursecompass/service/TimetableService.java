package com.example.coursecompass.service;

import com.example.coursecompass.dao.TimetableDao;
import com.example.coursecompass.model.Timetable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {
    private final TimetableDao timetableDao;

    public TimetableService(TimetableDao timetableDao) {
        this.timetableDao = timetableDao;
    }

    public void addCourseToTimetable(Timetable timetable) {
        timetableDao.addCourseToTimetable(timetable);
    }

    public void removeCourseFromTimetable(Long userId, String courseName, Integer year, String semester) {
        timetableDao.removeCourseFromTimetable(userId, courseName, year, semester);
    }

    public void removeCourseFromTimetable(Long userId, String courseName) {
        timetableDao.removeCourseFromTimetable(userId, courseName);
    }

    public List<Timetable> findByUserId(Long userId) {
        return timetableDao.findByUserId(userId);
    }
}