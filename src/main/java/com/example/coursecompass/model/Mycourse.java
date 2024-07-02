package com.example.coursecompass.model;

public class Mycourse {

    private Long userId;
    private String courseProgram;
    private String courseCode;
    private String courseName;
    private String courseDescription;

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCourseProgram() {
        return courseProgram;
    }

    public void setCourseProgram(String courseProgram) {
        this.courseProgram = courseProgram;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
}