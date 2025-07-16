package com.gitcoachai.controller;

import com.gitcoachai.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/git")
public class GitController {

    private final GitService gitService;

    @Autowired
    public GitController(GitService gitService) {
        this.gitService = gitService;
    }

    @PostMapping("/command")
    public ResponseEntity<String> executeCommand(@RequestBody Map<String, String> request) {
        String command = request.get("command");
        String args = request.get("args");

        if (command == null || command.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Command is required.");
        }

        try {
            String result = gitService.executeCommand(command, args);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error executing command: " + e.getMessage());
        }
    }
}
