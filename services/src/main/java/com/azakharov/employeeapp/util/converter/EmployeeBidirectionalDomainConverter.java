package com.azakharov.employeeapp.util.converter;

import com.azakharov.employeeapp.domain.Employee;
import com.azakharov.employeeapp.domain.id.EmployeeId;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeeEntity;

import javax.inject.Inject;
import java.util.Optional;

public class EmployeeBidirectionalDomainConverter implements BidirectionalDomainConverter<Employee, EmployeeEntity> {

    private final EmployeePositionBidirectionalDomainConverter employeePositionConverter;

    @Inject
    public EmployeeBidirectionalDomainConverter(final EmployeePositionBidirectionalDomainConverter employeePositionConverter) {
        this.employeePositionConverter = employeePositionConverter;
    }

    @Override
    public Employee convertToDomain(EmployeeEntity entity) {
        final var id = Optional.ofNullable(entity.getId())
                               .map(EmployeeId::new);
        return new Employee(id,
                            entity.getFirstName(),
                            entity.getSurname(),
                            employeePositionConverter.convertToDomain(entity.getPositionEntity()));
    }

    @Override
    public EmployeeEntity convertToEntity(Employee domain) {
        final var id = domain.id()
                             .map(EmployeeId::value)
                             .orElse(null);
        return new EmployeeEntity(id,
                                  domain.firstName(),
                                  domain.surname(),
                                  employeePositionConverter.convertToEntity(domain.position()));
    }
}
