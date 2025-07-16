 package com.gitcoachai.controller;

import com.gitcoachai.service.GitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GitControllerTest {

    @Mock
    private GitService gitService;

    @InjectMocks
    private GitController gitController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteCommandSuccess() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("command", "git add");
        request.put("args", ".");

        when(gitService.executeCommand("git add", ".")).thenReturn("Executed: git add .");

        // Act
        ResponseEntity<String> response = gitController.executeCommand(request);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Executed: git add .", response.getBody());
    }

    @Test
    void testExecuteCommandBadRequest() {
        // Arrange
        Map<String, String> request = new HashMap<>();

        // Act
        ResponseEntity<String> response = gitController.executeCommand(request);

        // Assert
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Command is required.", response.getBody());
    }

    @Test
    void testExecuteCommandError() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("command", "git commit");
        request.put("args", "-m 'Initial commit'");

        when(gitService.executeCommand("git commit", "-m 'Initial commit'")).thenThrow(new RuntimeException("Simulated error"));

        // Act
        ResponseEntity<String> response = gitController.executeCommand(request);

        // Assert
        assertEquals(500, response.getStatusCode().value());
        assertEquals("Error executing command: Simulated error", response.getBody());
    }
}
