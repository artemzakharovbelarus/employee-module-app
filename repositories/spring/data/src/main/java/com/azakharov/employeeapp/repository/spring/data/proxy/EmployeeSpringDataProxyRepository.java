package com.azakharov.employeeapp.repository.spring.data.proxy;

import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeeEntity;
import com.azakharov.employeeapp.repository.spring.data.EmployeeSpringDataRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class EmployeeSpringDataProxyRepository implements EmployeeRepository {

    private final EmployeeSpringDataRepository employeeRepository;

    @Inject
    public EmployeeSpringDataProxyRepository(final EmployeeSpringDataRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Optional<EmployeeEntity> find(final Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeEntity save(final EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeEntity update(final EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(final Long id) {
        employeeRepository.deleteById(id);
    }
}