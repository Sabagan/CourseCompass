package com.example.coursecompass.controller;

import com.example.coursecompass.service.MycourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MycourseController {

    @Autowired
    private MycourseService mycourseService;

    @GetMapping("/courses")
    public List<String> getAllCourseNames() {
        List<String> courseNames = mycourseService.getAllCourseNames();
        return courseNames;
    }
}