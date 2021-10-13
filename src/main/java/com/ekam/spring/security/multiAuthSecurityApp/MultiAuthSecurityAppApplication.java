package com.ekam.spring.security.multiAuthSecurityApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class MultiAuthSecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiAuthSecurityAppApplication.class, args);
	}

}
