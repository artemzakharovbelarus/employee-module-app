package com.azakharov.employeeapp.rest.util.converter;

import com.azakharov.employeeapp.domain.Employee;
import com.azakharov.employeeapp.domain.id.EmployeeId;
import com.azakharov.employeeapp.rest.dto.EmployeeDto;
import com.azakharov.employeeapp.rest.view.EmployeeView;

import javax.inject.Inject;
import java.util.Optional;

public class EmployeeAllSideDomainConverter implements AllSideDomainConverter<EmployeeDto, Employee, EmployeeView> {

    private final EmployeePositionAllSideDomainConverter employeePositionAllSideConverter;

    @Inject
    public EmployeeAllSideDomainConverter(final EmployeePositionAllSideDomainConverter employeePositionAllSideConverter) {
        this.employeePositionAllSideConverter = employeePositionAllSideConverter;
    }

    @Override
    public Employee convertToDomain(EmployeeDto dto) {
        final var id = Optional.ofNullable(dto.getId()).map(EmployeeId::new);
        return new Employee(id,
                            dto.getFirstName(),
                            dto.getSurname(),
                            employeePositionAllSideConverter.convertToDomain(dto.getPositionDto()));
    }

    @Override
    public EmployeeView convertToView(Employee domain) {
        final var id = domain.getId()
                             .map(EmployeeId::value)
                             .orElse(null);
        return new EmployeeView(id,
                                domain.getFirstName(),
                                domain.getSurname(),
                                employeePositionAllSideConverter.convertToView(domain.getPosition()));
    }
}