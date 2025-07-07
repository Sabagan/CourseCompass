package com.example.coursecompass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
@EnableWebMvc
public class CourseCompassApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseCompassApplication.class, args);
//		try {
//			// Wait briefly for server to start
//			Thread.sleep(2000);
//			Desktop.getDesktop().browse(new URI("http://localhost:8080"));
//		} catch (Exception e) {
//			System.err.println("Failed to launch browser: " + e.getMessage());
//		}
	}

}
