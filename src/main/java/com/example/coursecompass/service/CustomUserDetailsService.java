//package com.example.coursecompass.service;
//
//import com.example.coursecompass.dao.UserDao;
//import com.example.coursecompass.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserDao userDao; // Your JPA repository or DAO
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Fetch user details from the database
//        User user = userDao.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        // Convert User to Spring Security's UserDetails
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                user.getAuthorities()
//        );
//    }
//
//}
