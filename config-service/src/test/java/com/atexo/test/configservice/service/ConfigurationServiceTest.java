package com.atexo.test.configservice.service;

import com.atexo.test.configservice.model.Configuration;
import com.atexo.test.configservice.repository.ConfigurationRepository;
import com.atexo.test.configservice.service.impl.ConfigurationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class ConfigurationServiceTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;

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

    /**
     * Tests the getAllConfigs method.
     */
    @Test
    void getAllConfigs() {
        when(configurationRepository.findAll()).thenReturn(configurations);
        List<Configuration> result = configurationService.getAllConfigs();
        assertEquals(2, result.size());
        verify(configurationRepository, times(1)).findAll();
    }

    /**
     * Tests the saveOrUpdateConfig method for a new configuration.
     */
    @Test
    void saveOrUpdateConfig_NewConfiguration() {
        Configuration newConfig = new Configuration();
        newConfig.setName("newConfig");
        newConfig.setConfigOrder(3);

        when(configurationRepository.save(any(Configuration.class))).thenReturn(newConfig);
        when(configurationService.getAllConfigs()).thenReturn(List.of(newConfig));

        List<Configuration> result = configurationService.saveOrUpdateConfig(List.of(newConfig));
        assertEquals(1, result.size());
        assertEquals("newConfig", result.get(0).getName());
        verify(configurationRepository, times(1)).save(any(Configuration.class));
    }

    /**
     * Tests the saveOrUpdateConfig method for updating an existing configuration.
     */
    @Test
    void saveOrUpdateConfig_sameConfigurationOrder() {
        Configuration existingConfig = configurations.get(0);
        existingConfig.setName("updatedConfig");

        when(configurationRepository.findById(existingConfig.getId())).thenReturn(Optional.of(existingConfig));
        when(configurationRepository.save(any(Configuration.class))).thenReturn(existingConfig);
        when(configurationService.getAllConfigs()).thenReturn(List.of(existingConfig));

        List<Configuration> result = configurationService.saveOrUpdateConfig(List.of(existingConfig));
        assertEquals(1, result.size());
        assertEquals("updatedConfig", result.get(0).getName());
        verify(configurationRepository, times(1)).findByName(existingConfig.getName());
        verify(configurationRepository, times(1)).save(any(Configuration.class));
    }

    /**
     * Tests the saveOrUpdateConfig method for missing not null parameters.
     */
    @Test
    void saveOrUpdateConfig_NameFieldShouldNotBeNull() {
        Configuration config = new Configuration();
        config.setConfigOrder(1);
        assertThrows(IllegalArgumentException.class, () -> configurationService.saveOrUpdateConfig(List.of(config)));
    }
    /**
     * Tests the saveOrUpdateConfig method for missing not null parameters.
     */
    @Test
    void saveOrUpdateConfig_OrderFieldShouldNotBeNull() {
        Configuration config = new Configuration();
        config.setName("nom");
        assertThrows(IllegalArgumentException.class, () -> configurationService.saveOrUpdateConfig(List.of(config)));
    }

    /**
     * Tests the saveOrUpdateConfig method for checking uniqueness of order config.
     */
    @Test
    void saveOrUpdateConfig_Order_uniqueness() {
        List<Configuration> configurations = new ArrayList<>();
        Configuration config1 = new Configuration();
        config1.setName("nom");
        config1.setConfigOrder(1);
        Configuration config2 = new Configuration();
        config2.setName("nom");
        config2.setConfigOrder(1);
        configurations.add(config1);
        configurations.add(config2);


        List<Configuration> result = configurationService.saveOrUpdateConfig(configurations);
        assertEquals(0, result.size());
        verify(configurationRepository, times(2)).save(any(Configuration.class));
    }

    /**
     * Tests the clearConfigs method.
     */
    @Test
    void clearConfigs() {
        doNothing().when(configurationRepository).deleteAll();

        configurationService.clearConfigs();
        verify(configurationRepository, times(1)).deleteAll();
    }
}