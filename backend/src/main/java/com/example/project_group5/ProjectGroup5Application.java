package com.example.project_group5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class ProjectGroup5Application {

    public static void main(String[] args) {
        SpringApplication.run(ProjectGroup5Application.class, args);
    }

}
