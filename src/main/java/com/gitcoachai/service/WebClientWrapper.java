package com.gitcoachai.service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientWrapper {

    private final WebClient webClient;

    public WebClientWrapper(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> get(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> post(String url, Object body) {
        return webClient.post()
                .uri(url)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class);
    }

    // Add other methods as needed
}
