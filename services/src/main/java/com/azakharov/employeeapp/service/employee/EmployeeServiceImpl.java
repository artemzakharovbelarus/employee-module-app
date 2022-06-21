package com.azakharov.employeeapp.service.employee;

import com.azakharov.employeeapp.domain.Employee;
import com.azakharov.employeeapp.domain.id.EmployeeId;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.azakharov.employeeapp.util.converter.EmployeeBidirectionalDomainConverter;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeBidirectionalDomainConverter employeeConverter;

    @Inject
    public EmployeeServiceImpl(final EmployeeRepository employeeRepository,
                               final EmployeeBidirectionalDomainConverter employeeConverter) {
        this.employeeRepository = employeeRepository;
        this.employeeConverter = employeeConverter;
    }

    @Override
    public Optional<Employee> find(EmployeeId employeeId) {
        return employeeRepository.find(employeeId.value())
                                 .map(employeeConverter::convertToDomain);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll()
                                 .stream()
                                 .map(employeeConverter::convertToDomain)
                                 .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Employee save(Employee employee) {
        final var savingEntity = employeeConverter.convertToEntity(employee);
        return employeeConverter.convertToDomain(employeeRepository.save(savingEntity));
    }

    @Override
    public Employee update(Employee employee) {
        final var savingEntity = employeeConverter.convertToEntity(employee);
        return employeeConverter.convertToDomain(employeeRepository.update(savingEntity));
    }

    @Override
    public void delete(EmployeeId id) {
        employeeRepository.delete(id.value());
    }
}