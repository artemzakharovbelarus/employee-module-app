package com.azakharov.employeeapp.repository.spring.data;

import com.azakharov.employeeapp.repository.jpa.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeSpringDataRepository extends JpaRepository<EmployeeEntity, Long> { }