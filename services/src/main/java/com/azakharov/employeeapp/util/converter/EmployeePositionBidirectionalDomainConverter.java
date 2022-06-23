package com.azakharov.employeeapp.util.converter;

import com.azakharov.employeeapp.domain.EmployeePosition;
import com.azakharov.employeeapp.domain.id.EmployeePositionId;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeePositionEntity;

import java.util.Optional;

public class EmployeePositionBidirectionalDomainConverter implements BidirectionalDomainConverter<EmployeePosition, EmployeePositionEntity> {

    @Override
    public EmployeePosition convertToDomain(EmployeePositionEntity entity) {
        final var id = Optional.ofNullable(entity.getId())
                               .map(EmployeePositionId::new);
        return new EmployeePosition(id, entity.getName());
    }

    @Override
    public EmployeePositionEntity convertToEntity(EmployeePosition domain) {
        final var id = domain.getId()
                             .map(EmployeePositionId::value)
                             .orElse(null);
        return new EmployeePositionEntity(id, domain.getName());
    }
}
