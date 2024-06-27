package com.example.coursecompass.controller;

import com.example.coursecompass.model.User;
import com.example.coursecompass.repository.UserRepository;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("message", "User registered successfully");
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("loggedInUser", user);
            return "profile";
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Invalid username or password");
            return "redirect:/login";
        }
    }
}
