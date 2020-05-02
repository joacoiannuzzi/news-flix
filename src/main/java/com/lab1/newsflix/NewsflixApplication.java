package com.lab1.newsflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EntityScan("com.lab1.newsflix.model")
@EnableJpaRepositories("com.lab1.newsflix.repository")
public class NewsflixApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsflixApplication.class, args);
    }

}
