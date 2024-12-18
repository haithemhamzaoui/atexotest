package com.atexo.test.generationservice.service;

import com.atexo.test.generationservice.model.Generation;

import java.util.List;
import java.util.Map;

public interface GenerationService {
    /**
     * Generates a unique identifier based on the provided inputData.
     *
     * @param inputData a map containing key-value pairs of inputData
     * @return a generated unique identifier as a String
     */
    String generate(final Map<String, String> inputData);

    /**
     * Get all generations.
     *
     * @return the list of generations
     */
    List<Generation> getAllGenerations();
}