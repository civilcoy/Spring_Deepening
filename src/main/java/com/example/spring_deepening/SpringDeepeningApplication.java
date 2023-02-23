package com.example.spring_deepening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringDeepeningApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDeepeningApplication.class, args);
	}

}
