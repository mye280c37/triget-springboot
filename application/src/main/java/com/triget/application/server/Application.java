package com.triget.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.triget.application.common",
        "com.triget.application.config",
        "com.triget.application.domain",
        "com.triget.application.oauth",
        "com.triget.application.service",
        "com.triget.application.utils",
        "com.triget.application.web",
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
