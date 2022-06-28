package com.azakharov.employeeapp.service.employeeposition;

import com.azakharov.employeeapp.domain.EmployeePosition;
import com.azakharov.employeeapp.domain.id.EmployeePositionId;
import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.util.converter.EmployeePositionBidirectionalDomainConverter;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeePositionServiceImpl implements EmployeePositionService {

    private final EmployeePositionRepository employeePositionRepository;

    private final EmployeePositionBidirectionalDomainConverter employeePositionConverter;

    @Inject
    public EmployeePositionServiceImpl(final EmployeePositionRepository employeePositionRepository,
                                       final EmployeePositionBidirectionalDomainConverter employeePositionConverter) {
        this.employeePositionRepository = employeePositionRepository;
        this.employeePositionConverter = employeePositionConverter;
    }

    @Override
    public Optional<EmployeePosition> find(final EmployeePositionId id) {
        return employeePositionRepository.find(id.value())
                                         .map(employeePositionConverter::convertToDomain);
    }

    @Override
    public List<EmployeePosition> findAll() {
        return employeePositionRepository.findAll()
                                         .stream()
                                         .map(employeePositionConverter::convertToDomain)
                                         .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public EmployeePosition save(final EmployeePosition position) {
        final var savingEntity = employeePositionConverter.convertToEntity(position);
        return employeePositionConverter.convertToDomain(employeePositionRepository.save(savingEntity));
    }

    @Override
    public EmployeePosition update(final EmployeePosition position) {
        final var savingEntity = employeePositionConverter.convertToEntity(position);
        return employeePositionConverter.convertToDomain(employeePositionRepository.update(savingEntity));
    }

    @Override
    public void delete(final EmployeePositionId id) {
        employeePositionRepository.delete(id.value());
    }
}