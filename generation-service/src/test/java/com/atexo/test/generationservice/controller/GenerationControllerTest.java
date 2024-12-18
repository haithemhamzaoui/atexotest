package com.atexo.test.generationservice.controller;

import com.atexo.test.generationservice.service.GenerationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GenerationControllerTest {

    @Mock
    private GenerationService generationService;

    @InjectMocks
    private GenerationController generationController;

    private Map<String, String> inputData;

    /**
     * Sets up the test data before each test.
     */
    @BeforeEach
    void setUp() {
        inputData = new HashMap<>();
        inputData.put("nom", "hamzaoui");
        inputData.put("prenom", "haithem");
    }

    /**
     * Tests the generate method.
     */
    @Test
    void testGenerate() {
        String generatedId = "generatedId123";
        when(generationService.generate(inputData)).thenReturn(generatedId);

        ResponseEntity<String> response = generationController.generate(inputData);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(generatedId, response.getBody());
        verify(generationService, times(1)).generate(inputData);
    }
}