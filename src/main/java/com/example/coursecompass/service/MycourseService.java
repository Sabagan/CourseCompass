package com.example.coursecompass.service;

import com.example.coursecompass.dao.MycourseDao;
import com.example.coursecompass.model.Mycourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MycourseService {

    @Autowired
    private MycourseDao mycourseDao;

    public void save(Mycourse mycourse) {
        mycourseDao.save(mycourse);
    }

//    public List<Mycourse> findAll() {
//        return mycourseDao.findAll();
//    }

//    public Mycourse findById(Long id) {
//        return mycourseDao.findById(id);
//    }

    public List<String> getAllCourseNames() {
        return mycourseDao.getAllCourseNames();
    }

    public List<Mycourse> findByUserId(Long userId) {
        return mycourseDao.findByUserId(userId);
    }

    public void delete(Long id, String courseCode) {
        mycourseDao.delete(id, courseCode);
    }

    public boolean exists(Long id, String courseCode) {
        return mycourseDao.exists(id, courseCode);
    }
}