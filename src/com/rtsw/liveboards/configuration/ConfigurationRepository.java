package com.rtsw.liveboards.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ConfigurationRepository {

    private static Logger L = LogManager.getLogger(ConfigurationRepository.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

    private static ConfigurationRepository repository;

    private Configuration configuration;

    private ConfigurationRepository() {
    }

    public static ConfigurationRepository getRepository() {
        if (repository == null) {
            repository = new ConfigurationRepository();
        }
        return (repository);
    }

    public void load(File file) throws Exception {
        JsonNode schema = objectMapper.readTree(ConfigurationRepository.class.getResource("/schema/configuration.json"));
        L.info("loaded schema for 'configuration'");
        JsonNode input = objectMapper.readTree(file);
        JsonValidator validator = factory.getValidator();
        ProcessingReport processingReport = validator.validate(schema, input);
        if (processingReport.isSuccess()) {
            configuration = objectMapper.treeToValue(input, Configuration.class);
            L.info(String.format("loaded configuration from input '%s'", file.getAbsolutePath()));
        } else {
            throw new Exception(String.format("input from '%s' does not pass validation", file.getAbsolutePath()));
        }
    }

    public Configuration get() {
        return (configuration);
    }

}
