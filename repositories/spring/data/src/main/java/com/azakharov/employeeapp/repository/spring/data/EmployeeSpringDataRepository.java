package com.azakharov.employeeapp.repository.spring.data;

import com.azakharov.employeeapp.repository.jpa.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSpringDataRepository extends JpaRepository<EmployeeEntity, Long> { }