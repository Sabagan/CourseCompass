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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;
import javax.imageio.ImageIO;

import java.util.List;

@Controller
public class AppController {

    private static final String CAPTCHA_KEY = "CAPTCHA_KEY";
    private final Random random = new Random();

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
    public String loginForm(HttpSession session, Model model) {
        String captchaText = generateCaptchaText();
        session.setAttribute(CAPTCHA_KEY, captchaText);
        String captchaImage = createCaptchaImage(captchaText);
        model.addAttribute("captchaImage", captchaImage);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam String captcha, Model model, HttpSession session) {
        String captchaText = (String) session.getAttribute(CAPTCHA_KEY);

        if (captchaText == null || !captchaText.equalsIgnoreCase(captcha)) {
            model.addAttribute("message", "CAPTCHA verification failed!");
            return loginForm(session, model);
        }

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

    private String generateCaptchaText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }

    private String createCaptchaImage(String text) {
        int width = 150;
        int height = 50;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Gradient Background
        Color color1 = new Color(84, 50, 215); // Light color
        Color color2 = new Color(255, 183, 128); // Darker color
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, width, height, color2);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);

        // Custom Text
        Font font = new Font("Arial", Font.BOLD, 40);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        // Draw Text with Shadow
        g2d.setColor(Color.GRAY);
        g2d.drawString(text, 12, 42); // Shadow
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 10, 40); // Main text

        // Noise lines
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2d.drawLine(x1, y1, x2, y2);
        }

        g2d.dispose();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}