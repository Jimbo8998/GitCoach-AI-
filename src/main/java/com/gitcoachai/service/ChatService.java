package com.gitcoachai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import reactor.core.publisher.Mono;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class ChatService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public ChatService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.openai.com/v1")
                .build();
    }

    public Mono<String> ask(String question) {
        JSONArray messages = new JSONArray()
                .put(new JSONObject()
                        .put("role", "user")
                        .put("content", question));

        JSONObject requestBody = new JSONObject()
                .put("model", "gpt-3.5-turbo")
                .put("messages", messages);

        return webClient.post()
                .uri("/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody.toString())
                .retrieve()
                .onStatus(status -> status.value() == 401, response -> {
                    throw new RuntimeException("Invalid API key");
                })
                .onStatus(status -> status.value() == 429, response -> {
                    throw new RuntimeException("Rate limit exceeded");
                })
                .bodyToMono(String.class)
                .map(response -> {
                    JSONObject responseJson = new JSONObject(response);
                    return responseJson
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                })
                .onErrorResume(e -> {
                    // Log the error and return a fallback message
                    System.err.println("Error during OpenAI API call: " + e.getMessage());
                    return Mono.just("Sorry, I couldn't process your request.");
                });
    }
}
