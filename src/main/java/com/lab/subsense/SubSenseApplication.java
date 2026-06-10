package com.lab.subsense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubSenseApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubSenseApplication.class, args);
    }
}