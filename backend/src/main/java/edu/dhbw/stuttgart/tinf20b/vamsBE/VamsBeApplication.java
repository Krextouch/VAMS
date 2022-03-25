package edu.dhbw.stuttgart.tinf20b.vamsBE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class VamsBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(VamsBeApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}

