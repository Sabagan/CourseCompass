package com.example.coursecompass.service;

import com.example.coursecompass.model.Course;
import com.example.coursecompass.model.User;
import com.example.coursecompass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void addCourseToUser(User user, Course course) {
        user.addCourse(course);
        userRepository.save(user);
    }
}
