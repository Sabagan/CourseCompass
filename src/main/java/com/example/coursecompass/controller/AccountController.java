package com.example.coursecompass.controller;

import com.example.coursecompass.model.User;
import com.example.coursecompass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/data")
    @ResponseBody
    public User getUserAccount(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        return userService.findUserByUsername(username);
    }

    @PostMapping("/update")
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

}
