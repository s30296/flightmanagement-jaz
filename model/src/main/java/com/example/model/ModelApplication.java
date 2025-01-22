package com.example.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class ModelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelApplication.class, args);
	}

}
