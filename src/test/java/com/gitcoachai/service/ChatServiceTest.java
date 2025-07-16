package com.gitcoachai.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatServiceTest {

    @Mock
    private WebClientWrapper webClientWrapper;

    @InjectMocks
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAskValidQuestion() {
        String question = "What is AI?";
        String expectedResponse = "AI stands for Artificial Intelligence.";

        when(webClientWrapper.post(eq("https://api.openai.com/v1/chat/completions"), anyString()))
                .thenReturn(Mono.just("{\"choices\":[{\"message\":{\"content\":\"AI stands for Artificial Intelligence.\"}}]}"));

        String response = chatService.ask(question).block();

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testAskInvalidApiKey() {
        String question = "What is AI?";

        when(webClientWrapper.post(eq("https://api.openai.com/v1/chat/completions"), anyString()))
                .thenThrow(new RuntimeException("Invalid API key"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> chatService.ask(question).block());
        assertEquals("Invalid API key", exception.getMessage());
    }

    @Test
    void testAskRateLimitExceeded() {
        String question = "What is AI?";

        when(webClientWrapper.post(eq("https://api.openai.com/v1/chat/completions"), anyString()))
                .thenThrow(new RuntimeException("Rate limit exceeded"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> chatService.ask(question).block());
        assertEquals("Rate limit exceeded", exception.getMessage());
    }
}
