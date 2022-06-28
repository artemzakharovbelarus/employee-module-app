package com.azakharov.employeeapp.repository.spring.data.proxy;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeePositionEntity;
import com.azakharov.employeeapp.repository.spring.data.EmployeePositionSpringDataRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class EmployeePositionSpringDataProxyRepository implements EmployeePositionRepository {

    private final EmployeePositionSpringDataRepository employeePositionRepository;

    @Inject
    public EmployeePositionSpringDataProxyRepository(final EmployeePositionSpringDataRepository employeePositionRepository) {
        this.employeePositionRepository = employeePositionRepository;
    }

    @Override
    public Optional<EmployeePositionEntity> find(final Long id) {
        return employeePositionRepository.findById(id);
    }

    @Override
    public List<EmployeePositionEntity> findAll() {
        return employeePositionRepository.findAll();
    }

    @Override
    public EmployeePositionEntity save(final EmployeePositionEntity position) {
        return employeePositionRepository.save(position);
    }

    @Override
    public EmployeePositionEntity update(final EmployeePositionEntity position) {
        return employeePositionRepository.save(position);
    }

    @Override
    public void delete(final Long id) {
        employeePositionRepository.deleteById(id);
    }
}
