package com.gitcoachai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final WebClient webClient = WebClient.create("https://api.openai.com/v1/chat/completions");

    public Mono<String> ask(String question) {
        logger.info("Received question: {}", question);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");

        Map<String, String> message = Map.of(
                "role", "user",
                "content", question
        );
        requestBody.put("messages", List.of(message));

        return webClient.post()
                .header("Authorization", "Bearer " + openaiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    logger.debug("API Response: {}", response);
                    List<?> choices = (List<?>) response.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<?, ?> firstChoice = (Map<?, ?>) choices.get(0);
                        Map<?, ?> messageMap = (Map<?, ?>) firstChoice.get("message");
                        String answer = (String) messageMap.get("content");
                        logger.info("Returning answer: {}", answer);
                        return answer;
                    } else {
                        logger.warn("No choices found in response.");
                        return "No answer found.";
                    }
                });
    }
}
