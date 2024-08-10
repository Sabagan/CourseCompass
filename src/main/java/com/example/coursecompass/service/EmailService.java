//package com.example.coursecompass.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendContactEmail(String name, String email, String message) {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo("support@coursecompass.com");
//        mailMessage.setSubject("New Contact Form Submission");
//        mailMessage.setText("Name: " + name + "\nEmail: " + email + "\nMessage: " + message);
//        mailSender.send(mailMessage);
//    }
//}
