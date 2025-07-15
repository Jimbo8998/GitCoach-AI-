package com.gitcoachai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebClientConfig.class);

    @Bean
    public WebClient.Builder webClientBuilder() {
        logger.info("Creating WebClient.Builder bean");
        return WebClient.builder()
                .filter((request, next) -> {
                    logger.info("Request: {} {}", request.method(), request.url());
                    return next.exchange(request)
                            .doOnNext(response -> logger.info("Response: {}", response.statusCode()));
                });
    }
}
