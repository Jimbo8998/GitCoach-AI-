package com.gitcoachai.controller;

import com.gitcoachai.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AIController {

    private static final Logger logger = LoggerFactory.getLogger(AIController.class);

    private final ChatService chatService;

    @Autowired
    public AIController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ask")
    public Mono<ResponseEntity<String>> askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        logger.info("API called with question: {}", question);

        if (question == null || question.trim().isEmpty()) {
            logger.warn("Invalid question received");
            return Mono.just(ResponseEntity.badRequest().body("Question is required."));
        }

        return chatService.ask(question)
                .map(response -> {
                    logger.info("Response from ChatService: {}", response);
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(e -> {
                    logger.error("Error processing question: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.status(500).body("An error occurred while processing your request."));
                });
    }
}
