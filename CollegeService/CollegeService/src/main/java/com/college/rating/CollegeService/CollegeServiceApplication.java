package com.college.rating.CollegeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CollegeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollegeServiceApplication.class, args);
	}

}
