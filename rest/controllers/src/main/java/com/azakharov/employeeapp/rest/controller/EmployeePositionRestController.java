package com.azakharov.employeeapp.rest.controller;

import com.azakharov.employeeapp.api.EmployeePositionController;
import com.azakharov.employeeapp.domain.id.EmployeePositionId;
import com.azakharov.employeeapp.rest.dto.EmployeePositionDto;
import com.azakharov.employeeapp.rest.util.converter.EmployeePositionAllSideDomainConverter;
import com.azakharov.employeeapp.rest.view.EmployeePositionView;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeePositionRestController implements EmployeePositionController<EmployeePositionDto, EmployeePositionView> {

    private final EmployeePositionService employeePositionService;

    private final EmployeePositionAllSideDomainConverter employeePositionConverter;

    @Inject
    public EmployeePositionRestController(final EmployeePositionService employeePositionService,
                                          final EmployeePositionAllSideDomainConverter employeePositionConverter) {
        this.employeePositionService = employeePositionService;
        this.employeePositionConverter = employeePositionConverter;
    }

    @Override
    public EmployeePositionView get(Long id) {
        return employeePositionService.find(new EmployeePositionId(id))
                                      .map(employeePositionConverter::convertToView)
                                      .orElse(null);
    }

    @Override
    public List<EmployeePositionView> getAll() {
        return employeePositionService.findAll()
                                      .stream()
                                      .map(employeePositionConverter::convertToView)
                                      .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public EmployeePositionView save(EmployeePositionDto employeePositionDto) {
        final var position = employeePositionConverter.convertToDomain(employeePositionDto);
        return employeePositionConverter.convertToView(employeePositionService.save(position));
    }

    @Override
    public EmployeePositionView update(EmployeePositionDto employeePositionDto) {
        final var position = employeePositionConverter.convertToDomain(employeePositionDto);
        return employeePositionConverter.convertToView(employeePositionService.update(position));
    }

    @Override
    public void delete(Long id) {
        employeePositionService.delete(new EmployeePositionId(id));
    }
}
