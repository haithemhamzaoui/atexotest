package com.atexo.test.generationservice.service;

import com.atexo.test.generationservice.repository.GenerationRepository;
import com.atexo.test.generationservice.service.impl.GenerationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GenerationServiceTest {

    @Mock
    private GenerationRepository generationRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GenerationServiceImpl generationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerate() {
        // Mock configuration data
        List<Map<String, Object>> mockConfigurations = List.of(
                Map.of("name", "nom", "prefix", "", "suffix", "-", "length", 3, "configOrder", 2, "initValue", 0),
                Map.of("name", "prenom", "prefix", "", "suffix", "_", "length",4 , "configOrder", 1, "initValue", 0),
                Map.of("name", "birthDay", "prefix", "N", "suffix", "", "length", 0, "configOrder", 4, "initValue", 0),
                Map.of("name", "compteur", "prefix", "C", "suffix", "", "length", 5, "configOrder", 3, "initValue", 7)
        );

        // Mock generation data
        Map<String, String> inputData = Map.of("nom", "Isaac", "prenom", "ANTOINE", "birthDay", "1992");


        // Mock RestTemplate response
        generationService.setConfigServiceUrl("http://config-service-url");
        when(restTemplate.getForObject("http://config-service-url", List.class)).thenReturn(mockConfigurations);
        when(generationService.fetchConfigurations()).thenReturn(mockConfigurations);


        // Call the method under test
        String result = generationService.generate(inputData);

        // Verify behavior
        assertEquals("ANTO_Isa-C00008N1992", result, "Generated string does not match expected value");

        // Verify RestTemplate was called once
        verify(restTemplate, times(1)).getForObject("http://config-service-url", List.class);
    }
    @Test
    void testGenerate_notEquals() {
        // Mock configuration data
        List<Map<String, Object>> mockConfigurations = List.of(
                Map.of("name", "nom", "prefix", "", "suffix", "-", "length", 3, "configOrder", 2, "initValue", 0),
                Map.of("name", "prenom", "prefix", "", "suffix", "_", "length",4 , "configOrder", 4, "initValue", 0),
                Map.of("name", "birthDay", "prefix", "N", "suffix", "", "length", 0, "configOrder", 3, "initValue", 0),
                Map.of("name", "compteur", "prefix", "C", "suffix", "", "length", 5, "configOrder", 1, "initValue", 7)
        );

        // Mock generation data
        Map<String, String> inputData = Map.of("nom", "Isaac", "prenom", "ANTOINE", "birthDay", "1992");


        // Mock RestTemplate response
        generationService.setConfigServiceUrl("http://config-service-url");
        when(restTemplate.getForObject("http://config-service-url", List.class)).thenReturn(mockConfigurations);
        when(generationService.fetchConfigurations()).thenReturn(mockConfigurations);


        // Call the method under test
        String result = generationService.generate(inputData);

        // Verify behavior
        assertNotEquals("PRE-val-SUF-1001", result, "Generated string does not match expected value");

    }
}
