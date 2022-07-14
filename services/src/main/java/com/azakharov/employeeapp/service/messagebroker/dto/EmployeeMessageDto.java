package com.azakharov.employeeapp.service.messagebroker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeMessageDto(@JsonProperty("first_name") String firstName,
                                 @JsonProperty("surname") String surname,
                                 @JsonProperty("position_name") String positionName) {

    @JsonCreator
    public EmployeeMessageDto { }

    @Override
    public String toString() {
        return "EmployeeMessageDto{" +
               "firstName='" + firstName + '\'' +
               ", surname='" + surname + '\'' +
               ", positionName='" + positionName + '\'' +
               '}';
    }
}
