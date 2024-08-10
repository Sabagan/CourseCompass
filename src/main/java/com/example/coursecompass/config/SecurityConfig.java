//package com.example.coursecompass.config;
//
//import com.example.coursecompass.service.CustomUserDetailsService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/register", "/verify").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
//        return http.build();
//    }
//
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // Return your custom UserDetailsService implementation
//        return new CustomUserDetailsService();
//    }
//
//    @Bean
//    public AuthenticationManagerBuilder authenticationManagerBuilder(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(http.getSharedObject(AuthenticationManagerBuilder.class).getObject());
//        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//        return authenticationManagerBuilder;
//    }
//
//}
