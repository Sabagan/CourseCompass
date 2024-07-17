package com.example.coursecompass.controller;

import com.example.coursecompass.model.Course;
import com.example.coursecompass.model.Mycourse;
import com.example.coursecompass.model.User;
import com.example.coursecompass.service.CourseService;
import com.example.coursecompass.service.MycourseService;
import com.example.coursecompass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MycourseService mycourseService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/plan")
    public String showPlan(Model model, HttpSession session) {
        return "plan";
    }

    @GetMapping("/welcome")
    public String showWelcome() {
        return "welcome";
    }

    @GetMapping("/account")
    public String showAccount() {
        return "account";
    }

    @GetMapping("/account/data")
    @ResponseBody
    public User getUserAccount(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        return userService.findUserByUsername(username);
    }

    @PostMapping("/account/update")
    @ResponseBody
    public Map<String, Object> updateAccount(@RequestBody User updatedUser, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User currentUser = userService.findUserByUsername(username);

        Map<String, Object> response = new HashMap<>();

        if (currentUser != null) {
            // Update fields
            currentUser.setName(updatedUser.getName());
            currentUser.setDob(updatedUser.getDob());
            currentUser.setEmail(updatedUser.getEmail());

            userService.saveUser(currentUser);

            // Return success response
            response.put("success", true);
        } else {
            // Return error response
            response.put("success", false);
        }
        return response;
    }

    @GetMapping("/recommendations")
    public String showRecommendations() {
        return "recommendations";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.saveUser(user);

        redirectAttributes.addFlashAttribute("message", "User registered successfully");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", username);
            model.addAttribute("username", user.getUsername());
            List<Course> courses = courseService.getCourses();
            model.addAttribute("courses", courses);
            return "redirect:/welcome";
        }
        else {
            model.addAttribute("message", "Invalid username or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }

        User user = userService.findUserByUsername(username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("courses", courseService.getCourses());
        return "profile";
    }

    @GetMapping("/mycourses")
    public String showMyCourses(Model model, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null)
            return "redirect:/login";

        List<Mycourse> courses = mycourseService.findAll();
        model.addAttribute("courses", courses);
        return "mycourses";
    }

    @PostMapping("/addCourse")
    public String addCourse(@RequestParam("courseProgram") String courseProgram,
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
        }
        // Redirect back to profile page after adding course
        return "redirect:/profile";
    }

    @PostMapping("/deleteCourse")
    public String deleteCourse(@RequestParam("courseCode") String courseCode, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        User user = userService.findUserByUsername(username);
        Long userId = user.getId();

        mycourseService.delete(userId, courseCode);
        return "redirect:/mycourses";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}