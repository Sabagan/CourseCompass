package com.example.coursecompass.model;

public class Course {
    private String course_program;
    private String course_code;
    private String course_name;
    private String course_description;

    public Course() {

    }

    public Course(String course_program, String course_code, String course_name, String course_description) {
        this.course_program = course_program;
        this.course_code = course_code;
        this.course_name = course_name;
        this.course_description = course_description;
    }

    public String getCourse_program() {
        return course_program;
    }

    public void setCourse_program(String course_program) {
        this.course_program = course_program;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }
}
