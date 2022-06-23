package com.azakharov.employeeapp.rest.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeView(Long id,
                           String firstName,
                           String surname,
                           EmployeePositionView positionView) {

    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @JsonProperty("first_name")
    public String firstName() {
        return firstName;
    }

    @JsonProperty("surname")
    public String surname() {
        return surname;
    }

    @JsonProperty("position")
    public EmployeePositionView positionView() {
        return positionView;
    }

    @Override
    public String toString() {
        return "EmployeeView{" +
                "value=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", positionDto=" + positionView +
                '}';
    }
}
