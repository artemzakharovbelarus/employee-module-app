package com.azakharov.employeeapp.rest.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeePositionView(Long id, String name) {

    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @JsonProperty("name")
    public String name() {
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
