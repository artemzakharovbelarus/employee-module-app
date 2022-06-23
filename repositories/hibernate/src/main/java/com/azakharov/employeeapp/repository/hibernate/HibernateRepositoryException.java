package com.azakharov.employeeapp.repository.hibernate;

import com.azakharov.employeeapp.repository.jpa.RepositoryException;

public class HibernateRepositoryException extends RepositoryException {

    public HibernateRepositoryException(final String templateMessage, final Object... params) {
        super(templateMessage, params);
    }
}
