package com.gitcoachai.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock WebClient.Builder behavior
        WebClient.RequestBodyUriSpec mockRequestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersSpec mockRequestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec mockResponseSpec = mock(WebClient.ResponseSpec.class);
        when(mockResponseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Mocked response"));
        when(mockRequestBodyUriSpec.bodyValue(anyString())).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockRequestHeadersSpec.header(anyString(), anyString())).thenReturn(mockRequestHeadersSpec);

        WebClient mockWebClient = mock(WebClient.class);
        when(mockWebClient.post()).thenReturn(mockRequestBodyUriSpec);

        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(mockWebClient);
    }

    @Test
    void testAskValidQuestion() {
        String question = "What is AI?";
        String expectedResponse = "AI stands for Artificial Intelligence.";

        // Mock WebClient behavior here

        String response = chatService.ask(question).block();

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testAskInvalidApiKey() {
        String question = "What is AI?";

        // Mock WebClient behavior to simulate invalid API key

        RuntimeException exception = assertThrows(RuntimeException.class, () -> chatService.ask(question).block());
        assertEquals("Invalid API key", exception.getMessage());
    }

    @Test
    void testAskRateLimitExceeded() {
        String question = "What is AI?";

        // Mock WebClient behavior to simulate rate limit exceeded

        RuntimeException exception = assertThrows(RuntimeException.class, () -> chatService.ask(question).block());
        assertEquals("Rate limit exceeded", exception.getMessage());
    }
}
