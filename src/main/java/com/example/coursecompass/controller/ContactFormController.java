//package com.example.coursecompass.controller;
//
//import com.example.coursecompass.model.ContactForm;
////import com.example.coursecompass.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/submit-contact-form")
//public class ContactFormController {
//
//    @Autowired
//    private EmailService emailService;
//
//    @PostMapping
//    public String handleContactFormSubmission(ContactForm form) {
//        emailService.sendContactEmail(form.getName(), form.getEmail(), form.getMessage());
//        return "redirect:/home";  // Redirect to a thank you page or send a success response
//    }
//}
