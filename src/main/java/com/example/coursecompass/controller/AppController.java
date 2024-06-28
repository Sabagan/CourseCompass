package com.example.coursecompass.controller;

import com.example.coursecompass.model.Course;
import com.example.coursecompass.model.User;
import com.example.coursecompass.repository.UserRepository;
import com.example.coursecompass.service.CourseService;
import com.example.coursecompass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("username")
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public String home() {
        return "home";
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
    public String login(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = userService.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", username);
            return "redirect:/profile";
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Invalid username or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/profile")
    public String showProfilePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);

        List<Course> courses = courseService.getCourses();
        model.addAttribute("courses", courses);
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}