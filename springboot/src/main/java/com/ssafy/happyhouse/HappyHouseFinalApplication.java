package com.ssafy.happyhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HappyHouseFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyHouseFinalApplication.class, args);
	}

}
