package com.gitcoachai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GitCoachAiApplication {

    private static final Logger logger = LoggerFactory.getLogger(GitCoachAiApplication.class);

    public static void main(String[] args) {
        logger.info("Starting GitCoach AI Application...");
        SpringApplication.run(GitCoachAiApplication.class, args);
        logger.info("GitCoach AI Application started successfully.");
    }
}
