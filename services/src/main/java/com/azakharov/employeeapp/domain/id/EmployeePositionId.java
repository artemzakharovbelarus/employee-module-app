package com.azakharov.employeeapp.domain.id;

import com.azakharov.employeeapp.domain.exception.InvalidTypedIdException;

public record EmployeePositionId(Long value) {

    public EmployeePositionId {
        if (value == null) {
            throw new InvalidTypedIdException("Id can''t be null in {0}", this);
        }
    }

    @Override
    public String toString() {
        return "EmployeePositionId{" +
               "value=" + value +
               '}';
    }
}
