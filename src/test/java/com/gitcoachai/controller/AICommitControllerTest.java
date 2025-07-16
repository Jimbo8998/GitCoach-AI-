package com.gitcoachai.controller;

import com.gitcoachai.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AICommitControllerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private AICommitController aiCommitController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSuggestCommitMessageSuccess() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("changes", "Added new feature X");

        when(chatService.ask("Suggest a commit message for the following changes: Added new feature X"))
                .thenReturn(Mono.just("Implemented feature X"));

        // Act
        Mono<ResponseEntity<String>> responseMono = aiCommitController.suggestCommitMessage(request);
        ResponseEntity<String> response = responseMono.block();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Implemented feature X", response.getBody());
    }

    @Test
    void testSuggestCommitMessageBadRequest() {
        // Arrange
        Map<String, String> request = new HashMap<>();

        // Act
        Mono<ResponseEntity<String>> responseMono = aiCommitController.suggestCommitMessage(request);
        ResponseEntity<String> response = responseMono.block();

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Changes are required to suggest a commit message.", response.getBody());
    }

    @Test
    void testSuggestCommitMessageError() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("changes", "Refactored codebase");

        when(chatService.ask("Suggest a commit message for the following changes: Refactored codebase"))
                .thenReturn(Mono.error(new RuntimeException("Simulated error")));

        // Act
        Mono<ResponseEntity<String>> responseMono = aiCommitController.suggestCommitMessage(request);
        ResponseEntity<String> response = responseMono.block();

        // Assert
        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("An error occurred while suggesting a commit message.", response.getBody());
    }
}
