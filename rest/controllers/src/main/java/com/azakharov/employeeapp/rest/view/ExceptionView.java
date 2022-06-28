package com.azakharov.employeeapp.rest.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExceptionView(int httpCode, String message) {

    @JsonProperty("id")
    public int httpCode() {
        return httpCode;
    }

    @JsonProperty("message")
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return "ExceptionView{" +
                "httpCode=" + httpCode +
                ", message='" + message + '\'' +
                '}';
    }
}