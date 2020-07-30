package com.lab1.newsflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class NewsflixApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsflixApplication.class, args);
    }

}
