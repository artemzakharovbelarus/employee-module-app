package com.azakharov.employeeapp.rest.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeView {

    private final Long id;
    private final String firstName;
    private final String surname;
    private final EmployeePositionView positionView;

    public EmployeeView(final Long id,
                        final String firstName,
                        final String surname,
                        final EmployeePositionView positionView) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.positionView = positionView;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("surname")
    public String getSurname() {
        return surname;
    }

    @JsonProperty("position")
    public EmployeePositionView getPositionView() {
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
