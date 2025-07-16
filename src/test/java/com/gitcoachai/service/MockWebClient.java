package com.gitcoachai.service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class MockWebClient {

    public static WebClient createMockWebClient(String responseBody) {
        return WebClient.builder()
                .baseUrl("https://mock.api")
                .build();
    }

    public static WebClient.ResponseSpec mockResponseSpec(String responseBody) {
        return new WebClient.ResponseSpec() {
            @Override
            public Mono<String> bodyToMono(Class<String> clazz) {
                return Mono.just(responseBody);
            }

            @Override
            public WebClient.ResponseSpec onStatus(java.util.function.Predicate<org.springframework.http.HttpStatus> statusPredicate, java.util.function.Function<WebClient.ResponseSpec, Mono<? extends Throwable>> exceptionFunction) {
                return this;
            }
        };
    }
}
