package com.azakharov.employeeapp.util.json.exception;

import java.text.MessageFormat;

public class JsonException extends RuntimeException {

    public JsonException(final String templateMessage, final Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
