package com.example.coursecompass.controller;

import com.example.coursecompass.model.Mycourse;
import com.example.coursecompass.model.User;
import com.example.coursecompass.service.MycourseService;
import com.example.coursecompass.service.TimetableService;
import com.example.coursecompass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MycourseController {

    @Autowired
    private MycourseService mycourseService;

    @Autowired
    private UserService userService;

    @Autowired
    private TimetableService timetableService;

    @GetMapping("/courses")
    public List<String> getAllCourseNames() {
        List<String> courseNames = mycourseService.getAllCourseNames();
        return courseNames;
    }

    @PostMapping("/addCourse")
    public ResponseEntity<String> addCourse(@RequestParam("courseProgram") String courseProgram,
                                            @RequestParam("courseCode") String courseCode,
                                            @RequestParam("courseName") String courseName,
                                            @RequestParam("courseDescription") String courseDescription,
                                            HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long userId = loggedInUser.getId();

        if (!mycourseService.exists(userId, courseCode)) {
            // Create new Course object
            Mycourse newCourse = new Mycourse();
            newCourse.setUserId(userId);
            newCourse.setCourseCode(courseCode);
            newCourse.setCourseName(courseName);
            newCourse.setCourseDescription(courseDescription);
            newCourse.setCourseProgram(courseProgram);

            // Save the new course
            mycourseService.save(newCourse);
            return ResponseEntity.ok("Course added");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course already exists");
        }
    }

    @DeleteMapping("/deleteCourse")
    public ResponseEntity<String> deleteCourse(@RequestParam("courseCode") String courseCode,
                                               @RequestParam("courseName") String courseName,
                                               HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User user = userService.findUserByUsername(username);
        Long userId = user.getId();

        mycourseService.delete(userId, courseCode);
        timetableService.removeCourseFromTimetable(userId, courseName); // So delete course doesn't remain in the timetable

        return ResponseEntity.ok("Course deleted");
    }
}