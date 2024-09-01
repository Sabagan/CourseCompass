package com.example.coursecompass.service;

import com.example.coursecompass.dao.TimetablePlanDao;
import com.example.coursecompass.model.TimetablePlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetablePlanService {

    private final TimetablePlanDao timetablePlanDao;

    public TimetablePlanService(TimetablePlanDao timetablePlanDao) {
        this.timetablePlanDao = timetablePlanDao;
    }

    public void addTimetable(TimetablePlan timetablePlan) {
        timetablePlanDao.addTimetable(timetablePlan);
    }

    public void updateTimetable(Long userId, Integer timetableId, Integer years) {
        timetablePlanDao.updateTimetable(userId, timetableId, years);
    }

    public void deleteTimetable(Long userId, Integer timetableId) {
        timetablePlanDao.deleteTimetable(userId, timetableId);
    }

    public List<Integer> getTimetableIdsByUser(Long userId) {
        return timetablePlanDao.getTimetableIdsByUser(userId);
    }

    public Integer getYearsForTimetable(Long userId, Integer timetableId) {
        return timetablePlanDao.getYearsForTimetable(userId, timetableId);
    }
}
