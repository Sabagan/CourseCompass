package com.example.coursecompass.controller;

import com.example.coursecompass.model.Mycourse;
import com.example.coursecompass.model.User;
import com.example.coursecompass.service.MycourseService;
import com.example.coursecompass.service.TimetableCourseService;
import com.example.coursecompass.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    private TimetableCourseService timetableService;

    @Operation(summary = "Get all the Course Names",
            description = "Get the names of all the courses")
    @GetMapping("/courses")
    public List<String> getAllCourseNames() {
        List<String> courseNames = mycourseService.getAllCourseNames();
        return courseNames;
    }

    @Operation(summary = "Adds the Course",
            description = "Course is added to users myCourses tab. Upon success, the appropriate message is returned. If course is not added, it means course already exists.")
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

    @Operation(summary = "Deletes the Course",
            description = "Deletes the Course from myCourses tab and from the timetables that contains those courses")
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