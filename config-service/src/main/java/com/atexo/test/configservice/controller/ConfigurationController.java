package com.atexo.test.configservice.controller;

import com.atexo.test.configservice.dto.ConfigurationRequest;
import com.atexo.test.configservice.model.Configuration;
import com.atexo.test.configservice.service.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing configurations.
 */
@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
@RequestMapping("/api/config")
public class ConfigurationController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

    @Autowired
    private ConfigurationService configurationService;

    /**
     * GET /api/config : Get all configurations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of configurations in body
     */
    @GetMapping
    public ResponseEntity<List<Configuration>> getAllConfigs() {
        logger.info("Get all configurations");
        return ResponseEntity.ok(configurationService.getAllConfigs());
    }

    /**
     * POST /api/config : Add new configurations.
     *
     * @param configurationRequests the list of configuration requests to add
     * @return the ResponseEntity with status 200 (OK) and the list of added configurations in body
     */
    @PostMapping
    public ResponseEntity<List<Configuration>> addConfig(@RequestBody @Valid List<ConfigurationRequest> configurationRequests) {
        logger.info("Request to add new configurations: {}", configurationRequests);

        List<Configuration> configs = configurationRequests.stream().map(request ->
                new Configuration(
                        null, // ID will be generated
                        request.getName(),
                        request.getDisplay(),
                        request.getPrefix(),
                        request.getSuffix(),
                        request.getLength(),
                        request.getConfigOrder(),
                        request.getInitValue()
                )
        ).collect(Collectors.toList());

        List<Configuration> result = configurationService.saveOrUpdateConfig(configs);
        logger.info("Configurations added: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * DELETE /api/config/reset : Reset all configurations.
     *
     * @return the ResponseEntity with status 200 (OK) and a message indicating all configurations have been reset
     */
    @DeleteMapping("/reset")
    public ResponseEntity<String> resetConfigs() {
        logger.info("Request to reset all configurations");
        configurationService.clearConfigs();
        logger.info("All configurations have been reset");
        return ResponseEntity.ok("All configurations have been reset!");
    }
}