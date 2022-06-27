package com.azakharov.employeeapp.repository.spring.jdbc;

import com.azakharov.employeeapp.repository.jpa.RepositoryException;

public class SpringJdbcRepositoryException extends RepositoryException {

    public SpringJdbcRepositoryException(final String templateMessage, final Object... params) {
        super(templateMessage, params);
    }
}
