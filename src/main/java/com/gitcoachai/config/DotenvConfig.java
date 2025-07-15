package com.gitcoachai.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DotenvConfig {

    private static final Logger logger = LoggerFactory.getLogger(DotenvConfig.class);

    @PostConstruct
    public void init() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./")
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();

            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                logger.info("Loaded environment variable: {}", entry.getKey());
            });
        } catch (Exception e) {
            logger.error("Error initializing environment variables: {}", e.getMessage(), e);
        }
    }
}
