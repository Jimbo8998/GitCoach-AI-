package com.gitcoachai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class ChatService {

    private final WebClientWrapper webClientWrapper;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public ChatService(WebClientWrapper webClientWrapper) {
        this.webClientWrapper = webClientWrapper;
    }

    public Mono<String> ask(String question) {
        JSONArray messages = new JSONArray()
                .put(new JSONObject()
                        .put("role", "user")
                        .put("content", question));

        JSONObject requestBody = new JSONObject()
                .put("model", "gpt-3.5-turbo")
                .put("messages", messages);

        System.out.println("Request Body: " + requestBody.toString());

        return webClientWrapper.post("https://api.openai.com/v1/chat/completions", requestBody.toString())
                .map(response -> {
                    System.out.println("Response Body: " + response);
                    JSONObject responseJson = new JSONObject(response);
                    return responseJson
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                })
                .onErrorResume(e -> {
                    System.err.println("Error during OpenAI API call: " + e.getMessage());
                    return Mono.just("Sorry, I couldn't process your request.");
                });
    }
}
