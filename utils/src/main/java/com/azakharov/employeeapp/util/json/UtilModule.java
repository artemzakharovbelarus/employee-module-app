package com.azakharov.employeeapp.util.json;

import com.azakharov.employeeapp.util.json.json.JsonUtil;
import com.azakharov.employeeapp.util.json.json.JsonUtilImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class UtilModule extends AbstractModule {

    @Provides
    @Singleton
    public JsonUtil provideJsonUtil(final ObjectMapper objectMapper) {
        return new JsonUtilImpl(objectMapper);
    }

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }
}
