package com.azakharov.employeeapp.domain;

import com.azakharov.employeeapp.domain.exception.InvalidDomainException;
import com.azakharov.employeeapp.domain.id.EmployeePositionId;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public record EmployeePosition(Optional<EmployeePositionId> id, String name) {

    public EmployeePosition(final Optional<EmployeePositionId> id,
                            final String name) {
        this.id = id;
        this.name = name;

        validate();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var that = (EmployeePosition) o;
        return id.equals(that.id)
                && name.equals(that.name);
    }

    @Override
    public String toString() {
        return "EmployeePosition{" +
                "value=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    private void validate() {
        if (StringUtils.isBlank(name)) {
            throw new InvalidDomainException("name can''t be null or empty in {0}", this);
        }
    }
}
