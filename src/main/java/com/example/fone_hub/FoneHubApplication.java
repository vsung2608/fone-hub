package com.example.fone_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication
@Async
public class FoneHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoneHubApplication.class, args);
	}

}
