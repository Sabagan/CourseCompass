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

    public void save(Timetable timetable) {
        timetableDao.save(timetable);
    }

    public void removeCourseFromTimetable(Long userId, String courseName, Integer year, String semester) {
        // Implement logic to remove course from the timetable based on courseName, year, and semester
        timetableDao.removeCourseFromTimetable(userId, courseName, year, semester);
    }

    public List<Timetable> findByUserId(Long userId) {
        return timetableDao.findByUserId(userId);
    }
}
