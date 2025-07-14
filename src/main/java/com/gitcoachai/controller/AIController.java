package com.gitcoachai.controller;

import com.gitcoachai.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<?> askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        logger.info("API called with question: {}", question);

        if (question == null || question.trim().isEmpty()) {
            logger.warn("Invalid question received");
            return ResponseEntity.badRequest().body("Question is required.");
        }

        return ResponseEntity.ok(chatService.ask(question));
    }
}
