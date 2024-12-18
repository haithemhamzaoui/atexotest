package com.atexo.test.configservice.service.impl;

import com.atexo.test.configservice.model.Configuration;
import com.atexo.test.configservice.repository.ConfigurationRepository;
import com.atexo.test.configservice.service.ConfigurationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service implementation for configurations.
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    @Autowired
    private ConfigurationRepository repository;


    @Override
    public List<Configuration> getAllConfigs() {
        logger.info("Fetch all configurations");
        return repository.findAll();
    }


    @Override
    public List<Configuration> saveOrUpdateConfig(final List<Configuration> configs) {
        logger.info("Saving or updating configurations: {}", configs);
        for (Configuration config : configs) {
            if (config.getConfigOrder() == null) {
                logger.error("Configuration order is required");
                throw new IllegalArgumentException("Configuration order is required");
            }
            if (Strings.isBlank(config.getName())) {
                logger.error("Configuration name is required");
                throw new IllegalArgumentException("Configuration name is required");
            }
            Configuration existingConfigOrder = repository.findByConfigOrder(config.getConfigOrder());
            Configuration existingConfig = repository.findByName(config.getName());
            //verify that the configuration with the same order does not exist
            if (existingConfigOrder == null) {
                if (existingConfig != null) {
                    logger.info("Updating existing configuration: {}", existingConfig);
                    existingConfig.setPrefix(config.getPrefix());
                    existingConfig.setSuffix(config.getSuffix());
                    existingConfig.setLength(config.getLength());
                    existingConfig.setConfigOrder(config.getConfigOrder());
                    existingConfig.setInitValue(config.getInitValue());
                    repository.save(existingConfig);
                } else {
                    logger.info("Saving new configuration: {}", config);
                    repository.save(config);
                }
            }
        }
        List<Configuration> allConfigs = this.getAllConfigs();
        logger.info("Configurations after save or update: {}", allConfigs);
        return allConfigs;
    }


    @Override
    public void clearConfigs() {
        logger.info("Clearing all configurations");
        repository.deleteAll();
        logger.info("All configurations have been cleared");
    }
}