package com.azakharov.employeeapp.rest.exception;

import java.text.MessageFormat;

public class JsonUtilException extends RuntimeException {

    public JsonUtilException(final String templateMessage, final Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
