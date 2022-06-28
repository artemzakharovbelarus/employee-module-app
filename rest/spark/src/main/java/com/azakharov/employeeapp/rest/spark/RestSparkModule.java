package com.azakharov.employeeapp.rest.spark;

import com.azakharov.employeeapp.ServiceModule;
import com.azakharov.employeeapp.api.EmployeeController;
import com.azakharov.employeeapp.api.EmployeePositionController;
import com.azakharov.employeeapp.rest.dto.EmployeeDto;
import com.azakharov.employeeapp.rest.dto.EmployeePositionDto;
import com.azakharov.employeeapp.rest.controller.EmployeePositionRestController;
import com.azakharov.employeeapp.rest.controller.EmployeeRestController;
import com.azakharov.employeeapp.rest.spark.exception.SparkExceptionHandler;
import com.azakharov.employeeapp.rest.spark.proxy.EmployeePositionSparkProxyRestController;
import com.azakharov.employeeapp.rest.spark.proxy.EmployeeSparkProxyRestController;
import com.azakharov.employeeapp.rest.util.JsonUtil;
import com.azakharov.employeeapp.rest.util.converter.EmployeeAllSideDomainConverter;
import com.azakharov.employeeapp.rest.util.converter.EmployeePositionAllSideDomainConverter;
import com.azakharov.employeeapp.rest.view.EmployeePositionView;
import com.azakharov.employeeapp.rest.view.EmployeeView;
import com.azakharov.employeeapp.service.employee.EmployeeService;
import com.azakharov.employeeapp.service.employee.EmployeeServiceImpl;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionService;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class RestSparkModule extends AbstractModule {

    @Override
    protected void configure() {
        super.install(new ServiceModule());

        bindJsonUtil();
        bindConverters();
        bindControllers();
        bindSparkProxyControllers();

        super.bind(SparkExceptionHandler.class);
    }

    private void bindJsonUtil() {
        super.bind(ObjectMapper.class);
        super.bind(JsonUtil.class);
    }

    private void bindConverters() {
        super.bind(EmployeePositionAllSideDomainConverter.class);
        super.bind(EmployeeAllSideDomainConverter.class);
    }

    private void bindControllers() {
        bindEmployeePositionController();
        bindEmployeeController();
    }

    private void bindSparkProxyControllers() {
        super.bind(EmployeeSparkProxyRestController.class);
        super.bind(EmployeePositionSparkProxyRestController.class);
    }

    private void bindEmployeePositionController() {
        super.bind(EmployeePositionService.class).to(EmployeePositionServiceImpl.class);
        super.bind(new TypeLiteral<EmployeePositionController<EmployeePositionDto, EmployeePositionView>>(){})
             .to(EmployeePositionRestController.class);
    }

    private void bindEmployeeController() {
        super.bind(EmployeeService.class).to(EmployeeServiceImpl.class);
        super.bind(new TypeLiteral<EmployeeController<EmployeeDto, EmployeeView>>(){})
             .to(EmployeeRestController.class);
    }
}