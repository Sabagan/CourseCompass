package com.example.coursecompass.controller;

import com.example.coursecompass.model.User;
import com.example.coursecompass.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Gets the User Account Data",
            description = "Gets the user's account information including username, password, email, name and date of birth")
    @GetMapping("/data")
    @ResponseBody
    public User getUserAccount(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        return userService.findUserByUsername(username);
    }

    @Operation(summary = "Updates Account Information",
            description = "Update the Account Information that is name, email and date of birth. If update is successful, we return success")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "User does not exist")
    })
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
