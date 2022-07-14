package com.azakharov.employeeapp.service.employee;

import com.azakharov.employeeapp.domain.Employee;
import com.azakharov.employeeapp.domain.EmployeePosition;
import com.azakharov.employeeapp.domain.id.EmployeeId;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeeEntity;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionService;
import com.azakharov.employeeapp.service.exception.EmployeeServiceException;
import com.azakharov.employeeapp.util.converter.EmployeeBidirectionalDomainConverter;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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

        return upsert(employeeRepository::save, employee);
    }

    @Override
    public Employee update(final Employee employee) {
        checkEmployeeOnExisting(employee);
        checkPositionOnExisting(employee.position());

        return upsert(employeeRepository::update, employee);
    }

    @Override
    public void delete(final EmployeeId id) {
        employeeRepository.delete(id.value());
    }

    private Employee upsert(final Function<EmployeeEntity, EmployeeEntity> upsertAction, final Employee employee) {
        final var savingEntity = employeeConverter.convertToEntity(employee);
        return employeeConverter.convertToDomain(upsertAction.apply(savingEntity));
    }

    private void checkEmployeeOnExisting(final Employee employee) {
        final var id = employee.id()
                               .orElseThrow(()-> new EmployeeServiceException("Employee ID can't be null during checking on existing"));
        final var checkedEmployee = find(id);

        if (checkedEmployee.isEmpty()) {
            throw new EmployeeServiceException("There is no employee with ID: {}", id);
        }
    }

    private void checkPositionOnExisting(final EmployeePosition position) {
        final var id = position.id().orElse(null);
        final var checkedPosition = employeePositionService.find(id);

        if (checkedPosition.isEmpty()) {
            throw new EmployeeServiceException("There is no position with ID: {}", id);
        }
    }
}