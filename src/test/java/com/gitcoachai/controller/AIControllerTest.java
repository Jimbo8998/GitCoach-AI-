package com.gitcoachai.controller;

import com.gitcoachai.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AIControllerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private AIController aiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAskQuestionValidInput() {
        String question = "What is AI?";
        String expectedResponse = "AI stands for Artificial Intelligence.";

        when(chatService.ask(question)).thenReturn(Mono.just(expectedResponse));

        ResponseEntity<?> response = aiController.askQuestion(Map.of("question", question)).block();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testAskQuestionInvalidInput() {
        ResponseEntity<?> response = aiController.askQuestion(Map.of("question", "")).block();

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Question is required.", response.getBody());
    }

    @Test
    void testAskQuestionServiceError() {
        String question = "What is AI?";

        when(chatService.ask(question)).thenThrow(new RuntimeException("Service error"));

        ResponseEntity<?> response = aiController.askQuestion(Map.of("question", question)).block();

        assertEquals(500, response.getStatusCode().value());
        assertEquals("An error occurred while processing your request.", response.getBody());
    }
}
