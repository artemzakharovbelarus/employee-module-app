package com.azakharov.employeeapp.service.employee;

import com.azakharov.employeeapp.domain.Employee;
import com.azakharov.employeeapp.domain.EmployeePosition;
import com.azakharov.employeeapp.domain.id.EmployeeId;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionService;
import com.azakharov.employeeapp.service.exception.EmployeeServiceException;
import com.azakharov.employeeapp.util.converter.EmployeeBidirectionalDomainConverter;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeePositionService employeePositionService;

    private final EmployeeBidirectionalDomainConverter employeeConverter;

    @Inject
    public EmployeeServiceImpl(final EmployeeRepository employeeRepository,
                               final EmployeePositionService employeePositionService,
                               final EmployeeBidirectionalDomainConverter employeeConverter) {
        this.employeeRepository = employeeRepository;
        this.employeePositionService = employeePositionService;
        this.employeeConverter = employeeConverter;
    }

    @Override
    public Optional<Employee> find(final EmployeeId id) {
        return employeeRepository.find(id.value())
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
    public Employee save(final Employee employee) {
        checkPositionOnExisting(employee.position());

        final var savingEntity = employeeConverter.convertToEntity(employee);
        return employeeConverter.convertToDomain(employeeRepository.save(savingEntity));
    }

    @Override
    public Employee update(final Employee employee) {
        checkPositionOnExisting(employee.position());

        final var savingEntity = employeeConverter.convertToEntity(employee);
        return employeeConverter.convertToDomain(employeeRepository.update(savingEntity));
    }

    @Override
    public void delete(final EmployeeId id) {
        employeeRepository.delete(id.value());
    }

    private void checkPositionOnExisting(final EmployeePosition position) {
        final var positionId = position.id().orElse(null);
        final var checkedPosition = employeePositionService.find(positionId);

        if (checkedPosition.isEmpty()) {
            throw new EmployeeServiceException("There is no position with ID: {}", positionId);
        }
    }
}