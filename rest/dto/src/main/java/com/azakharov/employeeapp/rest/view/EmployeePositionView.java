package com.azakharov.employeeapp.rest.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeePositionView {

    private final Long id;
    private final String name;

    public EmployeePositionView(final Long id,
                                final String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "EmployeePositionView{" +
                "value=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
