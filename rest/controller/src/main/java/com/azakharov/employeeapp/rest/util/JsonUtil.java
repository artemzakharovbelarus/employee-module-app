package com.azakharov.employeeapp.rest.util;

import com.azakharov.employeeapp.rest.exception.JsonUtilException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private final ObjectMapper mapper;

    @Inject
    public JsonUtil(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String write(final Object dto) {
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception during writing object to JSON, message: {}", e.getMessage());
            LOGGER.debug("Exception during writing object to JSON, object: {}", dto, e);
            throw new JsonUtilException("Exception during writing object to JSON, message: {0}", e.getMessage());
        }
    }

    public String writeAll(final List<?> dtos) {
        try {
            return mapper.writeValueAsString(dtos);
        } catch (final JsonProcessingException e) {
            LOGGER.error("Exception during writing objects to JSON, message: {}", e.getMessage());
            LOGGER.debug("Exception during writing objects to JSON, objects: {}", dtos, e);
            throw new JsonUtilException("Exception during writing objects to JSON, message: {0}", e.getMessage());
        }
    }

    public <DTO> DTO read(final String json, Class<DTO> dtoClass) {
        try {
            return mapper.readValue(json, dtoClass);
        } catch (final IOException e) {
            LOGGER.error("Exception during parsing JSON string, message: {}", e.getMessage());
            LOGGER.debug("Exception during parsing JSON string, JSON: {}", json, e);
            throw new JsonUtilException("Exception during parsing JSON InputStream, message: {0}", e.getMessage());
        }
    }

    public <DTO> List<DTO> readAll(final InputStream json, Class<DTO> dtoClass) {
        final var jsonStr = convertToJsonStr(json);
        try {
            final var type = mapper.getTypeFactory().constructCollectionType(List.class, dtoClass);
            return mapper.readValue(jsonStr, type);
        } catch (final IOException e) {
            LOGGER.error("Exception during parsing JSON InputStream, message: {}", e.getMessage());
            LOGGER.debug("Exception during parsing JSON InputStream, JSON: {}", jsonStr, e);
            throw new JsonUtilException("Exception during parsing JSON InputStream, message: {0}", e.getMessage());
        }
    }

    private String convertToJsonStr(final InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
