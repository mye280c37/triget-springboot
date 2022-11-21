package com.triget.application.server;

import com.triget.application.server.config.properties.AppProperties;
import com.triget.application.server.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {
        "com.triget.application.server.common",
        "com.triget.application.server.config",
        "com.triget.application.server.controller",
        "com.triget.application.server.domain",
        "com.triget.application.server.entity",
        "com.triget.application.server.oauth",
        "com.triget.application.server.repository",
        "com.triget.application.server.service",
        "com.triget.application.server.utils",
})
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
