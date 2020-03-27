package com.aseemsavio.covid19informationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Covid19informationsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(Covid19informationsystemApplication.class, args);
    }

}
