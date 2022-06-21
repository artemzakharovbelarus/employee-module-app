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
        final var id = Optional.ofNullable(dto.getId()).map(EmployeePositionId::new);
        return new EmployeePosition(id, dto.getName());
    }

    @Override
    public EmployeePositionView convertToView(EmployeePosition domain) {
        final var id = domain.getId()
                             .map(EmployeePositionId::value)
                             .orElse(null);
        return new EmployeePositionView(id, domain.getName());
    }
}