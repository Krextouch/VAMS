package edu.dhbw.stuttgart.tinf20b.vamsBE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VamsBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VamsBeApplication.class, args);
    }
}

