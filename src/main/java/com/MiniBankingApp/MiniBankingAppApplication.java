package com.MiniBankingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class MiniBankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniBankingAppApplication.class, args);
	}

}
