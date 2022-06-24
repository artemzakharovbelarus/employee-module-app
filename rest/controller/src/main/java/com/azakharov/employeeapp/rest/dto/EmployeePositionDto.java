package com.azakharov.employeeapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeePositionDto(@JsonProperty("id") Long id,
                                  @JsonProperty("name") String name) {

    @JsonCreator
    public EmployeePositionDto { }

    @Override
    public String toString() {
        return "EmployeePositionDto{" +
                "value=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
