package com.example.coursecompass.model;

public class TimetablePlan {

    private Long userId;
    private int timetableId;
    private int years;

    public TimetablePlan() {}

    public TimetablePlan(Long userId, int timetableId, int years) {
        this.userId = userId;
        this.timetableId = timetableId;
        this.years = years;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }
}
