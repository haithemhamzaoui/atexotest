package com.atexo.test.configservice.repository;

import com.atexo.test.configservice.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    /**
     * Find a configuration by name.
     *
     * @param name the name of the configuration
     * @return the configuration with the given name
     */
    Configuration findByName(final String name);
    /**
     * Find a configuration by configOrder.
     *
     * @param configOrder the order of the configuration
     * @return the configuration with the given name
     */
    Configuration findByConfigOrder(final int configOrder);
}