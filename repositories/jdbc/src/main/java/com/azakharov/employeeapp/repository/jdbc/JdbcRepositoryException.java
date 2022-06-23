package com.azakharov.employeeapp.repository.jdbc;

import com.azakharov.employeeapp.repository.jpa.RepositoryException;

public class JdbcRepositoryException extends RepositoryException {

    public JdbcRepositoryException(final String templateMessage, final Object... params) {
        super(templateMessage, params);
    }
}
