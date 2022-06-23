package com.azakharov.employeeapp.service.exception;

import java.text.MessageFormat;

public class ServiceException extends RuntimeException {

    public ServiceException(final String templateMessage, final Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
