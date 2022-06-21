package com.azakharov.employeeapp.rest.spark.controller;

import com.azakharov.employeeapp.api.EmployeeController;
import com.azakharov.employeeapp.domain.id.EmployeeId;
import com.azakharov.employeeapp.rest.dto.EmployeeDto;
import com.azakharov.employeeapp.rest.util.converter.EmployeeAllSideDomainConverter;
import com.azakharov.employeeapp.rest.view.EmployeeView;
import com.azakharov.employeeapp.service.employee.EmployeeService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeRestController implements EmployeeController<EmployeeDto, EmployeeView> {

    private final EmployeeService employeeService;

    private final EmployeeAllSideDomainConverter employeeConverter;

    @Inject
    public EmployeeRestController(final EmployeeService employeeService,
                                  final EmployeeAllSideDomainConverter employeeConverter) {
        this.employeeService = employeeService;
        this.employeeConverter = employeeConverter;
    }

    @Override
    public EmployeeView get(Long id) {
        return employeeService.find(new EmployeeId(id))
                              .map(employeeConverter::convertToView)
                              .orElse(null);
    }

    @Override
    public List<EmployeeView> getAll() {
        return employeeService.findAll()
                              .stream()
                              .map(employeeConverter::convertToView)
                              .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public EmployeeView save(EmployeeDto employeeDto) {
        final var employee = employeeService.save(employeeConverter.convertToDomain(employeeDto));
        return employeeConverter.convertToView(employee);
    }

    @Override
    public EmployeeView update(EmployeeDto employeeDto) {
        final var employee = employeeService.update(employeeConverter.convertToDomain(employeeDto));
        return employeeConverter.convertToView(employee);
    }

    @Override
    public void delete(Long id) {
        employeeService.delete(new EmployeeId(id));
    }
}