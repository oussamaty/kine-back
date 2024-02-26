package com.kine.Kine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class KineApplication {

	public static void main(String[] args) {
		SpringApplication.run(KineApplication.class, args);
	}

}
