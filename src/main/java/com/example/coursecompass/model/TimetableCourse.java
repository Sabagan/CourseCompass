package com.example.coursecompass.model;

public class TimetableCourse {

    private Long userId;
    private int timetableId;
    private int year;
    private String semester;
    private String courseName;

    public TimetableCourse() {}

    public TimetableCourse(Long userId, int timetableId, int year, String semester, String courseName) {
        this.userId = userId;
        this.timetableId = timetableId;
        this.year = year;
        this.semester = semester;
        this.courseName = courseName;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}