package com.azakharov.employeeapp.service.exception;

public class EmployeeServiceException extends ServiceException {

    public EmployeeServiceException(final String templateMessage, final Object... params) {
        super(templateMessage, params);
    }
}
