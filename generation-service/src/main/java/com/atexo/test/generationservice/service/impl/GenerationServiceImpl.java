package com.atexo.test.generationservice.service.impl;

import com.atexo.test.generationservice.model.Generation;
import com.atexo.test.generationservice.repository.GenerationRepository;
import com.atexo.test.generationservice.service.GenerationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.atexo.test.generationservice.service.utils.Constant.*;

/**
 * Service interface for generation operations.
 */
@Service
public class GenerationServiceImpl implements GenerationService {
    private static final Logger logger = LoggerFactory.getLogger(GenerationServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;
    private AtomicInteger compteur;
    @Autowired
    private GenerationRepository repository;
    @Value("${config.service.url}")
    private String configServiceUrl;

    @Override
    public String generate(final Map<String, String> inputData) {
        logger.info("Starting generation with input data: {}", inputData);
        AtomicInteger compteurLength = new AtomicInteger();
        List<Map<String, Object>> configurations = fetchConfigurations();

        StringBuilder generationInscrit = new StringBuilder();

        configurations.stream()
                .sorted(Comparator.comparing(c -> ((Integer) c.get(CONFIGURATION_ORDER))))
                .forEach(config -> this.processConfiguration(config, inputData, generationInscrit, compteurLength));

        this.saveGeneration(generationInscrit.toString());

        logger.info("Generation completed: {}", generationInscrit);
        return generationInscrit.toString();
    }

    @Override
    public List<Generation> getAllGenerations() {
        logger.info("Fetching all generations");
        return repository.findAll();
    }

    /**
     * Fetches configurations from the configuration service.
     *
     * @return a list of configurations
     */
    public List<Map<String, Object>> fetchConfigurations() {
        logger.info("Fetching configurations from URL: {}", configServiceUrl);
        return restTemplate.getForObject(configServiceUrl, List.class);
    }

    /**
     * Processes a single configuration and appends the generated value to the generation string.
     *
     * @param config            the configuration map containing configuration details
     * @param inputData         the input data map containing user inputs
     * @param generationInscrit the StringBuilder to append the generated value
     * @param compteurLength    the AtomicInteger that store the length of the compteur
     */
    private void processConfiguration(final Map<String, Object> config, final Map<String, String> inputData, final StringBuilder generationInscrit, final AtomicInteger compteurLength) {
        String name = (String) config.get(CONFIGURATION_NAME);
        String prefix = (String) config.get(CONFIGURATION_PREFIX);
        String suffix = (String) config.get(CONFIGURATION_SUFFIX);
        int length = (Integer) config.get(CONFIGURATION_LENGTH);
        int initValue = (Integer) config.get(CONFIGURATION_INITVALUE);

        String value = inputData.getOrDefault(name, "");
        value = (length != 0 && value.length() > length) ? value.substring(0, length) : value;

        if (!Strings.isBlank(prefix)) {
            generationInscrit.append(prefix);
        }
        generationInscrit.append(value);
        if (!Strings.isBlank(suffix) && !name.equals(CONFIGURATION_COMPTEUR)) {
            generationInscrit.append(suffix);
        }
        if (name.equals(CONFIGURATION_COMPTEUR)) {
            if (this.compteur == null) {
                this.compteur = new AtomicInteger(initValue);
            }
            compteurLength.set(length);
            this.appendCompteurValue(generationInscrit, compteurLength);
            //add suffix compteur after incrimenting the compteur
            if (!Strings.isBlank(suffix)) {
                generationInscrit.append(suffix);
            }
        }
    }

    /**
     * Appends the compteur value if compteur is not null, if compteur length is greater than 0, it will be formatted.
     *
     * @param generationInscrit the generation string builder
     * @param compteurLength    the compteur length
     */
    public void appendCompteurValue(final StringBuilder generationInscrit, final AtomicInteger compteurLength) {
        if (compteur != null) {
            generationInscrit.append((compteurLength.get() > 0) ? String.format("%0" + compteurLength + "d", compteur.incrementAndGet()) : compteur.incrementAndGet());
        }
    }

    /**
     * Saves the generated value to the database.
     *
     * @param generationValue the generated value
     */
    public void saveGeneration(final String generationValue) {
        logger.info("Saving generation value: {}", generationValue);
        Generation newGeneration = new Generation(null, generationValue);
        repository.save(newGeneration);
    }

    public void setCompteur(final AtomicInteger compteur) {
        this.compteur = compteur;
    }

    public void setConfigServiceUrl(final String configServiceUrl) {
        this.configServiceUrl = configServiceUrl;
    }
}