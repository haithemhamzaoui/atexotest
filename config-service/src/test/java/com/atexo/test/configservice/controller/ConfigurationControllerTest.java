package com.atexo.test.configservice.controller;

import com.atexo.test.configservice.dto.ConfigurationRequest;
import com.atexo.test.configservice.model.Configuration;
import com.atexo.test.configservice.service.ConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ConfigurationControllerTest {

    @Mock
    private ConfigurationService configurationService;

    @InjectMocks
    private ConfigurationController configurationController;

    private List<Configuration> configurations;

    @BeforeEach
    void setUp() {
        configurations = new ArrayList<>();
        Configuration config1 = new Configuration();
        config1.setId(1L);
        config1.setName("nom");
        config1.setConfigOrder(1);

        Configuration config2 = new Configuration();
        config2.setId(2L);
        config2.setName("prenom");
        config2.setConfigOrder(2);

        configurations.add(config1);
        configurations.add(config2);
    }

    @Test
    void testGetAllConfigs() {
        when(configurationService.getAllConfigs()).thenReturn(configurations);

        ResponseEntity<List<Configuration>> response = configurationController.getAllConfigs();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(configurationService, times(1)).getAllConfigs();
    }

    @Test
    void testAddConfig() {
        when(configurationService.saveOrUpdateConfig(anyList())).thenReturn(configurations);

        List<ConfigurationRequest> requests = new ArrayList<>();
        ConfigurationRequest request1 = new ConfigurationRequest();
        request1.setName("nom");
        request1.setConfigOrder(1);
        requests.add(request1);

        ResponseEntity<List<Configuration>> response = configurationController.addConfig(requests);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(configurationService, times(1)).saveOrUpdateConfig(anyList());
    }


    @Test
    void testResetConfigs() {
        doNothing().when(configurationService).clearConfigs();

        ResponseEntity<String> response = configurationController.resetConfigs();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All configurations have been reset!", response.getBody());
        verify(configurationService, times(1)).clearConfigs();
    }
}