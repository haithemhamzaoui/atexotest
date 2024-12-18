package com.atexo.test.configservice.service;

import com.atexo.test.configservice.model.Configuration;

import java.util.List;

public interface ConfigurationService {
    /**
     * Get all configurations.
     *
     * @return the list of configurations
     */
    List<Configuration> getAllConfigs();

    /**
     * Save or update configurations. if configuration exist, il will modify it, else create new one
     *
     * @param configs the list of configurations to save or update
     * @return the list of saved or updated configurations
     */
    List<Configuration> saveOrUpdateConfig(final List<Configuration> configs);

    /**
     * Clear all configurations.
     */
    void clearConfigs();
}