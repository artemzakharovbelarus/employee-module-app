package com.azakharov.employeeapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeDto(@JsonProperty("id") Long id,
                          @JsonProperty("first_name") String firstName,
                          @JsonProperty("surname") String surname,
                          @JsonProperty("position") EmployeePositionDto positionDto) {

    @JsonCreator
    public EmployeeDto { }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "value=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", positionDto=" + positionDto +
                '}';
    }
}