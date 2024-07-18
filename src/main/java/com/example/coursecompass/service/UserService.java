package com.example.coursecompass.service;

import com.example.coursecompass.dao.UserDao;
import com.example.coursecompass.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Transactional
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

//    @Transactional
//    public void addCourseToUser(User user, Course course) {
//        user.addCourse(course);
//        userDao.save(user);
//    }
}
