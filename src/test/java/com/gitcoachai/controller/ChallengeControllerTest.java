package com.gitcoachai.controller;

import com.gitcoachai.service.ChallengeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChallengeControllerTest {

    @Mock
    private ChallengeService challengeService;

    @InjectMocks
    private ChallengeController challengeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChallenges() {
        // Arrange
        List<String> mockChallenges = List.of("Create a new branch", "Commit changes to the branch", "Merge the branch into main");
        when(challengeService.getChallenges()).thenReturn(mockChallenges);

        // Act
        ResponseEntity<List<String>> response = challengeController.getChallenges();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockChallenges, response.getBody());
    }

    @Test
    void testUpdateProgressSuccess() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("challenge", "Create a new branch");

        // Act
        ResponseEntity<String> response = challengeController.updateProgress(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Progress updated for challenge: Create a new branch", response.getBody());
    }

    @Test
    void testUpdateProgressBadRequest() {
        // Arrange
        Map<String, String> request = new HashMap<>();

        // Act
        ResponseEntity<String> response = challengeController.updateProgress(request);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Challenge is required.", response.getBody());
    }

    @Test
    void testGetProgress() {
        // Arrange
        List<String> mockProgress = List.of("Create a new branch");
        when(challengeService.getProgress()).thenReturn(mockProgress);

        // Act
        ResponseEntity<List<String>> response = challengeController.getProgress();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockProgress, response.getBody());
    }
}
