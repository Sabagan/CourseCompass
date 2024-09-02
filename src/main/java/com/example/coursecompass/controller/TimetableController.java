package com.example.coursecompass.controller;

import com.example.coursecompass.model.Mycourse;
import com.example.coursecompass.model.TimetableCourse;
import com.example.coursecompass.model.TimetablePlan;
import com.example.coursecompass.model.User;
import com.example.coursecompass.service.MycourseService;
import com.example.coursecompass.service.TimetableCourseService;
import com.example.coursecompass.service.TimetablePlanService;
import com.example.coursecompass.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    @Autowired
    private TimetableCourseService timetableService;

    @Autowired
    private TimetablePlanService timetablePlanService;

    @Autowired
    private UserService userService;

    @Autowired
    private MycourseService mycourseService;

    public TimetableController(TimetableCourseService timetableService, TimetablePlanService timetablePlanService, UserService userService, MycourseService mycourseService) {
        this.timetableService = timetableService;
        this.timetablePlanService = timetablePlanService;
        this.userService = userService;
        this.mycourseService = mycourseService;
    }

    @Tag(name = "TimetableCourseServiceController", description = "All methods involving the courses in the timetable")
    @Operation(summary = "Saves a Course to the Timetable",
            description = "Saves a TimetableCourse, consisting of only the course name, to a specific location based on the timetableId, year and semester.")
    @PostMapping("/save")
    public void saveTimetable(@RequestBody TimetableCourse timetableCourse, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        timetableCourse.setUserId(loggedInUser.getId());
        timetableService.addCourseToTimetable(timetableCourse);
    }

    @Tag(name = "TimetablePlanServiceController", description = "All methods involving the timetables on the plan")
    @Operation(summary = "Adds a timetable and gives the ID",
            description = "Adds a timetable and returns the appropriate ID so operations can be performed for the specific timetable")
    @PostMapping("/newTimetable")
    public Integer newTimetableToPlan(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long id = loggedInUser.getId();

        TimetablePlan timetablePlan = new TimetablePlan();
        timetablePlan.setUserId(id);
        timetablePlan.setYears(1);
        // No Need for SetTimetablePlanId since it auto increments

        timetablePlanService.addTimetable(timetablePlan);

        return timetablePlan.getTimetableId();
    }

    @Tag(name = "TimetableCourseServiceController", description = "All methods involving the courses in the timetable")
    @Operation(summary = "Update a Timetable",
            description = "Updates a Timetable based on the timetableId. The update changes the number of years for the specific timetable.")
    @PostMapping("/updateTimetable{timetableId}?{years}")
    public void updateTimetable(@Parameter(description = "ID of Timetable", required = true) @PathVariable Integer timetableId,
                                @Parameter(description = "Number of Years for the Timetable", required = true) @PathVariable Integer years,
                                HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long id = loggedInUser.getId();

        Integer prevYears = timetablePlanService.getYearsForTimetable(id, timetableId);
        while (years < prevYears) {
            timetableService.removeCourseFromTimetable(id, timetableId, prevYears);
            prevYears--;
        }

        timetablePlanService.updateTimetable(id, timetableId, years);
    }

    @Tag(name = "TimetableCourseServiceController", description = "All methods involving the courses in the timetable")
    @Operation(summary = "Gets all the Available Courses",
            description = "Gets all the Courses that are in the Users myCourses cart but not in the specific timetable")
    @GetMapping("/availableCourses{timetableId}")
    public List<Mycourse> getAvailableCourses(@Parameter(description = "ID of Timetable", required = true) @PathVariable("timetableId") Integer timetableId,
                                              HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long userId = loggedInUser.getId();

        List<Mycourse> myCourses = mycourseService.findByUserId(userId);
        List<TimetableCourse> timetableCourse = timetableService.findByUserTimetableId(userId, timetableId);
        return myCourses.stream()
                .filter(course -> timetableCourse.stream().noneMatch(entry -> entry.getCourseName().equals(course.getCourseName())))
                .collect(Collectors.toList());
    }

    @Tag(name = "TimetablePlanServiceController", description = "All methods involving the timetables on the plan")
    @Operation(summary = "Gets all the TimetableIds",
            description = "Gets all the TimetableIds for a user")
    @GetMapping("/allTimetableIds")
    public List<Integer> getTimetableIds(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long userId = loggedInUser.getId();

        return timetablePlanService.getTimetableIdsByUser(userId);
    }

    @Tag(name = "TimetablePlanServiceController", description = "All methods involving the timetables on the plan")
    @Operation(summary = "Get Number of Years for Timetable",
            description = "Get Number of Years for a Timetable given its timetableId")
    @GetMapping("/{timetable}")
    public Integer getNumberOfYears(@Parameter(description = "ID of Timetable", required = true) @PathVariable Integer timetable,
                                    HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long userId = loggedInUser.getId();

        return timetablePlanService.getYearsForTimetable(userId, timetable);
    }

    @Tag(name = "TimetableCourseServiceController", description = "All methods involving the courses in the timetable")
    @Operation(summary = "Gets all Courses on the Timetables",
            description = "Gets all Courses on the Timetables, each of which is distinguished by the timetableID")
    @GetMapping("/all")
//    @ResponseBody
    public List<TimetableCourse> getTimetable(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        return timetableService.findByUserId(loggedInUser.getId());
    }

    @Tag(name = "TimetableCourseServiceController", description = "All methods involving the courses in the timetable")
    @Operation(summary = "Removes a Course from a Timetable",
            description = "The specified course will be removed from the indicated timetable. The year and semester are not necessarily required but used to save time looking for the course.")
    @DeleteMapping("/remove")
    public void removeCourseFromTimetable(@RequestParam String courseName,
                                          @RequestParam int year,
                                          @RequestParam String semester,
                                          @RequestParam("timetable") int timetableId,
                                          HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long userId = loggedInUser.getId();

        timetableService.removeCourseFromTimetable(userId, timetableId, courseName, year, semester);
    }

    @Tag(name = "TimetablePlanServiceController", description = "All methods involving the timetables on the plan")
    @Operation(summary = "Removes a timetable",
            description = "The specified timetable will be removed from the plan. This will also involve removing the courses that were in that timetable originally.")
    @DeleteMapping("/removeTimetable")
    public void removeTimetable(@RequestParam int timetableId, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User loggedInUser = userService.findUserByUsername(username);
        Long userId = loggedInUser.getId();

        List<TimetableCourse> courses = timetableService.findByUserTimetableId(userId, timetableId);
        if (!courses.isEmpty()) {
            timetableService.removeCourseFromTimetable(userId, timetableId);
        }
        timetablePlanService.deleteTimetable(userId, timetableId);
    }
}