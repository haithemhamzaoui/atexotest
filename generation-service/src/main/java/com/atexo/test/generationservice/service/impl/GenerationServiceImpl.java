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
    private AtomicInteger counter;
    @Autowired
    private GenerationRepository repository;
    @Value("${config.service.url}")
    private String configServiceUrl;

    @Override
    public String generate(final Map<String, String> inputData) {
        logger.info("Starting generation with input data: {}", inputData);
        AtomicInteger counterLength = new AtomicInteger();
        List<Map<String, Object>> configurations = fetchConfigurations();

        StringBuilder generationInscrit = new StringBuilder();

        configurations.stream()
                .sorted(Comparator.comparing(c -> ((Integer) c.get(CONFIGURATION_ORDER))))
                .forEach(config -> this.processConfiguration(config, inputData, generationInscrit, counterLength));

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

    private void processConfiguration(final Map<String, Object> config, final Map<String, String> inputData, final StringBuilder generationInscrit, final AtomicInteger counterLength) {
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
        if (!Strings.isBlank(suffix)) {
            generationInscrit.append(suffix);
        }
        if (name.equals("compteur")) {
            if (this.counter == null) {
                this.counter = new AtomicInteger(initValue);
            }
            counterLength.set(length);
            this.appendCounterValue(generationInscrit, counterLength);
        }

    }

    /**
     * Appends the counter value if counter is not null, if counter length is greater than 0, it will be formatted.
     *
     * @param generationInscrit the generation string builder
     * @param counterLength     the counter length
     */
    public void appendCounterValue(final StringBuilder generationInscrit,final  AtomicInteger counterLength) {
        if (counter != null) {
            generationInscrit.append((counterLength.get() > 0) ? String.format("%0" + counterLength + "d", counter.incrementAndGet()) : counter.incrementAndGet());
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

    public void setCounter(final AtomicInteger counter) {
        this.counter = counter;
    }

    public void setConfigServiceUrl(final String configServiceUrl) {
        this.configServiceUrl = configServiceUrl;
    }
}