package com.example.ourgarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class OurGardenApplication {
    public static void main(String[] args) {
        SpringApplication.run(OurGardenApplication.class, args);
    }

}
