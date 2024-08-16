package com.example.coursecompass.controller;

import com.example.coursecompass.model.Mycourse;
import com.example.coursecompass.model.TimetableCourse;
import com.example.coursecompass.model.User;
import com.example.coursecompass.service.MycourseService;
import com.example.coursecompass.service.TimetableService;
import com.example.coursecompass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private UserService userService;

    @Autowired
    private MycourseService mycourseService;

    public TimetableController(TimetableService timetableService, UserService userService, MycourseService mycourseService) {
        this.timetableService = timetableService;
        this.userService = userService;
        this.mycourseService = mycourseService;
    }

    @PostMapping("/save")
    public void saveTimetable(@RequestBody TimetableCourse timetableCourse, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        timetableCourse.setUserId(loggedInUser.getId());
        timetableService.addCourseToTimetable(timetableCourse);
    }

    @GetMapping("/availableCourses")
    public List<Mycourse> getAvailableCourses(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long userId = loggedInUser.getId();

        List<Mycourse> myCourses = mycourseService.findByUserId(userId);
        List<TimetableCourse> timetableCourse = timetableService.findByUserId(userId);
        return myCourses.stream()
                .filter(course -> timetableCourse.stream().noneMatch(entry -> entry.getCourseName().equals(course.getCourseName())))
                .collect(Collectors.toList());
    }


    @GetMapping("/all")
    @ResponseBody
    public List<TimetableCourse> getTimetable(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        return timetableService.findByUserId(loggedInUser.getId());
    }

    @DeleteMapping("/remove")
    public void removeCourseFromTimetable(@RequestParam String courseName,
                                            @RequestParam int year,
                                            @RequestParam String semester,
                                            HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long userId = loggedInUser.getId();

        timetableService.removeCourseFromTimetable(userId, courseName, year, semester);
    }
}
