package com.azakharov.employeeapp.rest.spark.exception;

import com.azakharov.employeeapp.domain.exception.InvalidDomainException;
import com.azakharov.employeeapp.domain.exception.InvalidTypedIdException;
import com.azakharov.employeeapp.rest.view.ExceptionView;
import com.azakharov.employeeapp.service.exception.EmployeeServiceException;
import com.azakharov.employeeapp.util.json.json.JsonUtil;
import com.google.common.net.MediaType;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import javax.inject.Inject;

public class SparkExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparkExceptionHandler.class);

    private final JsonUtil jsonUtil;

    @Inject
    public SparkExceptionHandler(final JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    public void handleInvalidDomainException() {
        Spark.exception(InvalidDomainException.class, (e, request, response) -> {
            LOGGER.debug("InvalidDomainException was handled", e);

            response.status(HttpStatus.PRECONDITION_FAILED_412);
            response.type(MediaType.JSON_UTF_8.type());

            final var exceptionView = new ExceptionView(HttpStatus.PRECONDITION_FAILED_412, e.getMessage());
            response.body(jsonUtil.write(exceptionView));
        });
    }

    public void handleInvalidTypedIdException() {
        Spark.exception(InvalidTypedIdException.class, (e, request, response) -> {
            LOGGER.debug("InvalidTypedIdException was handled", e);

            response.status(HttpStatus.PRECONDITION_FAILED_412);
            response.type(MediaType.JSON_UTF_8.type());

            final var exceptionView = new ExceptionView(HttpStatus.PRECONDITION_FAILED_412, e.getMessage());
            response.body(jsonUtil.write(exceptionView));
        });
    }

    public void handleEmployeeServiceException() {
        Spark.exception(EmployeeServiceException.class, (e, request, response) -> {
            LOGGER.debug("EmployeeServiceException was handled", e);

            response.status(HttpStatus.PRECONDITION_FAILED_412);
            response.type(MediaType.JSON_UTF_8.type());

            final var exceptionView = new ExceptionView(HttpStatus.PRECONDITION_FAILED_412, e.getMessage());
            response.body(jsonUtil.write(exceptionView));
        });
    }

    public void handleUnexpectedException() {
        Spark.exception(RuntimeException.class, (e, request, response) -> {
            LOGGER.debug("Unexpected exception was handled", e);

            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            response.type(MediaType.JSON_UTF_8.type());

            final var exceptionView = new ExceptionView(HttpStatus.INTERNAL_SERVER_ERROR_500, "Unexpected server error");
            response.body(jsonUtil.write(exceptionView));
        });
    }
}
