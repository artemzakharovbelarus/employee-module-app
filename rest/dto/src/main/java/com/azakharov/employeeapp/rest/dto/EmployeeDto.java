package com.azakharov.employeeapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDto {

    private final Long id;
    private final String firstName;
    private final String surname;
    private final EmployeePositionDto positionDto;

    @JsonCreator
    public EmployeeDto(final @JsonProperty("id") Long id,
                       final @JsonProperty("first_name") String firstName,
                       final @JsonProperty("surname") String surname,
                       final @JsonProperty("position") EmployeePositionDto positionDto) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.positionDto = positionDto;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public EmployeePositionDto getPositionDto() {
        return positionDto;
    }

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