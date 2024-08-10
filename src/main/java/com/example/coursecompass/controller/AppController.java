package com.example.coursecompass.controller;

import com.example.coursecompass.model.Course;
import com.example.coursecompass.model.Mycourse;
import com.example.coursecompass.model.User;
import com.example.coursecompass.service.CourseService;
import com.example.coursecompass.service.MycourseService;
import com.example.coursecompass.service.TimetableService;
import com.example.coursecompass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MycourseService mycourseService;

    @Autowired
    private TimetableService timetableService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/plan")
    public String showPlan(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null)
            return "redirect:/login";
        return "plan";
    }

    @GetMapping("/welcome")
    public String showWelcome(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null)
            return "redirect:/login";
        return "welcome";
    }

    @GetMapping("/account")
    public String showAccount(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null)
            return "redirect:/login";
        return "account";
    }

    @GetMapping("/recommendations")
    public String showRecommendations(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null)
            return "redirect:/login";
        return "recommendations";
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

        User loggedInUser = userService.findUserByUsername(username);
        List<Mycourse> courses = mycourseService.findByUserId(loggedInUser.getId());

        model.addAttribute("courses", courses);
        return "mycourses";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userService.saveUser(user);

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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}