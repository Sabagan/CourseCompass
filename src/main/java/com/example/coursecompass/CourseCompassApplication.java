package com.example.coursecompass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class CourseCompassApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseCompassApplication.class, args);
	}

}
