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
        final var id = Optional.ofNullable(dto.id()).map(EmployeeId::new);
        return new Employee(id,
                            dto.firstName(),
                            dto.surname(),
                            employeePositionAllSideConverter.convertToDomain(dto.positionDto()));
    }

    @Override
    public EmployeeView convertToView(Employee domain) {
        final var id = domain.id()
                             .map(EmployeeId::value)
                             .orElse(null);
        return new EmployeeView(id,
                                domain.firstName(),
                                domain.surname(),
                                employeePositionAllSideConverter.convertToView(domain.position()));
    }
}