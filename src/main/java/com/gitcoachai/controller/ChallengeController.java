package com.gitcoachai.controller;

import com.gitcoachai.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getChallenges() {
        return ResponseEntity.ok(challengeService.getChallenges());
    }

    @PostMapping("/progress")
    public ResponseEntity<String> updateProgress(@RequestBody Map<String, String> request) {
        String challenge = request.get("challenge");

        if (challenge == null || challenge.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Challenge is required.");
        }

        challengeService.updateProgress(challenge);
        return ResponseEntity.ok("Progress updated for challenge: " + challenge);
    }

    @GetMapping("/progress")
    public ResponseEntity<List<String>> getProgress() {
        return ResponseEntity.ok(challengeService.getProgress());
    }
}
