package com.azakharov.employeeapp.domain;

import com.azakharov.employeeapp.domain.exception.InvalidDomainException;
import com.azakharov.employeeapp.domain.id.EmployeeId;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;

public class Employee {

    private final Optional<EmployeeId> id;
    private final String firstName;
    private final String surname;
    private final EmployeePosition position;

    public Employee(final Optional<EmployeeId> id,
                    final String firstName,
                    final String surname,
                    final EmployeePosition position) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.position = position;

        validate();
    }

    public Optional<EmployeeId> getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var employee = (Employee) o;
        return id.equals(employee.id)
            && firstName.equals(employee.firstName)
            && surname.equals(employee.surname)
            && position.equals(employee.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, surname, position);
    }

    @Override
    public String toString() {
        return "Employee{" +
               "value=" + id +
               ", firstName='" + firstName + '\'' +
               ", surname='" + surname + '\'' +
               ", position=" + position +
               '}';
    }

    private void validate() {
        if (StringUtils.isBlank(firstName)) {
            throw new InvalidDomainException("firstName can''t be null or empty in {0}", this);
        }

        if (StringUtils.isBlank(surname)) {
            throw new InvalidDomainException("surname can''t be null or empty in {0}", this);
        }

        if (position == null) {
            throw new InvalidDomainException("position can''t be null in {0}", this);
        }
    }
}
