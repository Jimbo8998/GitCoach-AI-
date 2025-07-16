package com.gitcoachai.controller;

import com.gitcoachai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/commit")
public class AICommitController {

    private final ChatService chatService;

    @Autowired
    public AICommitController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/suggest")
    public Mono<ResponseEntity<String>> suggestCommitMessage(@RequestBody Map<String, String> request) {
        String changes = request.get("changes");

        if (changes == null || changes.trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("Changes are required to suggest a commit message."));
        }

        return chatService.ask("Suggest a commit message for the following changes: " + changes)
                .map(response -> ResponseEntity.ok(response))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("An error occurred while suggesting a commit message.")));
    }
}
