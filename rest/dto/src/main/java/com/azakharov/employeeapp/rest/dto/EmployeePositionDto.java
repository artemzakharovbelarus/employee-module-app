package com.azakharov.employeeapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeePositionDto {

    private final Long id;
    private final String name;

    @JsonCreator
    public EmployeePositionDto(final @JsonProperty("id") Long id,
                               final @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "EmployeePositionDto{" +
               "value=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
