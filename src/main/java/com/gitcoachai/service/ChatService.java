package com.gitcoachai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class ChatService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    public ChatService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.openai.com/v1")
                .build();
    }

    public String ask(String question) {
        try {
            JSONArray messages = new JSONArray()
                    .put(new JSONObject()
                            .put("role", "user")
                            .put("content", question));

            JSONObject requestBody = new JSONObject()
                    .put("model", "gpt-3.5-turbo")
                    .put("messages", messages);

            String responseBody = webClient.post()
                    .uri("/chat/completions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject responseJson = new JSONObject(responseBody);
            return responseJson
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (WebClientResponseException e) {
            return "OpenAI API error: " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Internal error: " + e.getMessage();
        }
    }
}
