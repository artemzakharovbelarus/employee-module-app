package com.azakharov.employeeapp.domain.id;

import com.azakharov.employeeapp.domain.exception.InvalidTypedIdException;

public record EmployeeId(Long value) {

    public EmployeeId {
        if (value == null) {
            throw new InvalidTypedIdException("Id can''t be null in {0}", this);
        }
    }

    @Override
    public String toString() {
        return "EmployeeId{" +
               "value=" + value +
               '}';
    }
}
