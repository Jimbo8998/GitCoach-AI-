package com.gitcoachai.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitService {

    private final List<String> commandHistory = new ArrayList<>();

    public String executeCommand(String command, String args) {
        // Simulate Git command execution
        String result = "Executed: " + command + " " + (args != null ? args : "");

        // Store command in history
        commandHistory.add(result);

        return result;
    }

    public List<String> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }
}
