package com.azakharov.employeeapp.service.employee;

import com.azakharov.employeeapp.domain.Employee;
import com.azakharov.employeeapp.domain.id.EmployeeId;
import com.azakharov.employeeapp.service.CrudService;

public interface EmployeeService extends CrudService<Employee, EmployeeId> { }