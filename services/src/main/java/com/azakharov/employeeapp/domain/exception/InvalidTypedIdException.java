package com.azakharov.employeeapp.domain.exception;

import java.text.MessageFormat;

public class InvalidTypedIdException extends RuntimeException {

    public InvalidTypedIdException(final String templateMessage, final Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
