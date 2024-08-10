//package com.example.coursecompass.controller;
//
//import com.example.coursecompass.model.User;
//import com.example.coursecompass.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class RegistrationController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/verify")
//    public String verifyUser(@RequestParam("token") String token) {
//        User user = userService.findByVerificationToken(token);
//        if (user == null) {
//            return "Invalid verification token";
//        }
//
//        user.setEnabled(true);
//        user.setVerificationToken(null);
//        userService.saveUser(user);
//
//        return "Email verified successfully. You can now log in";
//    }
//}
