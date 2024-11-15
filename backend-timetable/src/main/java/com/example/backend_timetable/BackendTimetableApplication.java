package com.example.backend_timetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.backend_timetable", "com.example.backend_timetable.config"}) 	
public class BackendTimetableApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendTimetableApplication.class, args);
	}

}
