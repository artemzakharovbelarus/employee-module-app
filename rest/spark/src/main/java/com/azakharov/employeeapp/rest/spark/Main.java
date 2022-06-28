package com.azakharov.employeeapp.rest.spark;

import com.azakharov.employeeapp.rest.spark.exception.SparkExceptionHandler;
import com.azakharov.employeeapp.rest.spark.proxy.BaseSparkRestController;
import com.azakharov.employeeapp.rest.spark.proxy.EmployeePositionSparkProxyRestController;
import com.azakharov.employeeapp.rest.spark.proxy.EmployeeSparkProxyRestController;
import com.google.inject.Guice;

public class Main {

    public static void main(String[] args) {
        final var injector = Guice.createInjector(new RestSparkModule());

        initEmployeePositionEndpoints(injector.getInstance(EmployeePositionSparkProxyRestController.class));
        initEmployeeEndpoints(injector.getInstance(EmployeeSparkProxyRestController.class));
        initExceptionHandlers(injector.getInstance(SparkExceptionHandler.class));
    }

    private static void initEmployeePositionEndpoints(final EmployeePositionSparkProxyRestController employeePositionController) {
        employeePositionController.getEmployeePosition();
        employeePositionController.getAllEmployeePositions();
        employeePositionController.save();
        employeePositionController.update();
        employeePositionController.delete();
    }

    private static void initEmployeeEndpoints(final EmployeeSparkProxyRestController employeeController) {
        employeeController.getEmployee();
        employeeController.getAllEmployees();
        employeeController.save();
        employeeController.update();
        employeeController.delete();
    }

    private static void initExceptionHandlers(final SparkExceptionHandler handler) {
        handler.handleInvalidDomainException();
        handler.handleInvalidTypedIdException();
        handler.handleEmployeeServiceException();
        handler.handleUnexpectedException();
    }
}
