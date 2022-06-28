package com.azakharov.employeeapp.repository.eclipselink;

import com.azakharov.employeeapp.repository.jpa.RepositoryException;

public class EclipseLinkRepositoryException extends RepositoryException {

    public EclipseLinkRepositoryException(final String templateMessage, final Object... params) {
        super(templateMessage, params);
    }
}
