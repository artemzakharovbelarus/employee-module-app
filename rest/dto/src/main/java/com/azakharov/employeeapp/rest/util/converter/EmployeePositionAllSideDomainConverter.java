package com.azakharov.employeeapp.rest.util.converter;

import com.azakharov.employeeapp.domain.EmployeePosition;
import com.azakharov.employeeapp.domain.id.EmployeePositionId;
import com.azakharov.employeeapp.rest.dto.EmployeePositionDto;
import com.azakharov.employeeapp.rest.view.EmployeePositionView;

import java.util.Optional;

public class EmployeePositionAllSideDomainConverter
        implements AllSideDomainConverter<EmployeePositionDto, EmployeePosition, EmployeePositionView> {

    @Override
    public EmployeePosition convertToDomain(EmployeePositionDto dto) {
        final var id = Optional.ofNullable(dto.id()).map(EmployeePositionId::new);
        return new EmployeePosition(id, dto.name());
    }

    @Override
    public EmployeePositionView convertToView(EmployeePosition domain) {
        final var id = domain.id()
                             .map(EmployeePositionId::value)
                             .orElse(null);
        return new EmployeePositionView(id, domain.name());
    }
}