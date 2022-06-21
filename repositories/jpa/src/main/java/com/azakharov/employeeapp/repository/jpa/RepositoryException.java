package com.azakharov.employeeapp.repository.jpa;

import java.text.MessageFormat;

public class RepositoryException extends RuntimeException {

    public RepositoryException(final String templateMessage, final Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
