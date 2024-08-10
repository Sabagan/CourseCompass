package com.example.coursecompass.service;

import com.example.coursecompass.dao.UserDao;
import com.example.coursecompass.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Value("${spring.mail.username}")
//    private String fromEmail;
//
//    public void register(User user) {
//        user.setEnabled(false);
//        user.setVerificationToken(UUID.randomUUID().toString());
//        userDao.save(user);
//
//        sendVerificationEmail(user);
//    }
//
//    public User findByVerificationToken(String token) {
//        return userDao.findByVerificationToken(token);
//    }
//
//    private void sendVerificationEmail(User user) {
//        String subject = "Email Verification";
//        String confirmationUrl = "http://localhost:8080/verify?token=" + user.getVerificationToken();
//        String message = "Please verify your email by clicking the link below:\n" + confirmationUrl;
//
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(user.getEmail());
//        email.setSubject(subject);
//        email.setText(message);
//        email.setFrom(fromEmail);
//
//        mailSender.send(email);
//    }

    @Transactional
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Transactional
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
