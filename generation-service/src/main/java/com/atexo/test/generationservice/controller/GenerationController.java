package com.atexo.test.generationservice.controller;

import com.atexo.test.generationservice.model.Generation;
import com.atexo.test.generationservice.service.GenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
@RequestMapping("/api/generate")
public class GenerationController {
    private static final Logger logger = LoggerFactory.getLogger(GenerationController.class);
    @Autowired
    private GenerationService generationService;

    @PostMapping
    public ResponseEntity<String> generate(@RequestBody Map<String, String> inputData) {
        String generatedId = generationService.generate(inputData);
        return ResponseEntity.ok(generatedId);
    }

    @GetMapping
    public ResponseEntity<List<Generation>> allGenerations() {
        logger.info("Get all generations");
        return ResponseEntity.ok(generationService.getAllGenerations());
    }
}
