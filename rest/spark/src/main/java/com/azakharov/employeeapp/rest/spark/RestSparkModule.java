package com.azakharov.employeeapp.rest.spark;

import com.azakharov.employeeapp.ServiceModule;
import com.azakharov.employeeapp.api.EmployeeController;
import com.azakharov.employeeapp.api.EmployeePositionController;
import com.azakharov.employeeapp.rest.controller.EmployeePositionRestController;
import com.azakharov.employeeapp.rest.controller.EmployeeRestController;
import com.azakharov.employeeapp.rest.dto.EmployeeDto;
import com.azakharov.employeeapp.rest.dto.EmployeePositionDto;
import com.azakharov.employeeapp.rest.spark.exception.SparkExceptionHandler;
import com.azakharov.employeeapp.rest.spark.proxy.EmployeePositionSparkProxyRestController;
import com.azakharov.employeeapp.rest.spark.proxy.EmployeeSparkProxyRestController;
import com.azakharov.employeeapp.rest.util.converter.EmployeeAllSideDomainConverter;
import com.azakharov.employeeapp.rest.util.converter.EmployeePositionAllSideDomainConverter;
import com.azakharov.employeeapp.rest.view.EmployeePositionView;
import com.azakharov.employeeapp.rest.view.EmployeeView;
import com.azakharov.employeeapp.service.employee.EmployeeService;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionService;
import com.azakharov.employeeapp.util.json.UtilModule;
import com.azakharov.employeeapp.util.json.json.JsonUtil;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

public class RestSparkModule extends AbstractModule {

    @Provides
    @Singleton
    public EmployeePositionAllSideDomainConverter employeePositionAllSideDomainConverter() {
        return new EmployeePositionAllSideDomainConverter();
    }

    @Provides
    @Singleton
    public EmployeeAllSideDomainConverter employeeAllSideDomainConverter(final EmployeePositionAllSideDomainConverter positionConverter) {
        return new EmployeeAllSideDomainConverter(positionConverter);
    }

    @Provides
    @Singleton
    public EmployeeSparkProxyRestController employeeSparkProxyRestController(
            final EmployeeController<EmployeeDto, EmployeeView> employeeController,
            final JsonUtil jsonUtil) {
        return new EmployeeSparkProxyRestController(employeeController, jsonUtil);
    }

    @Provides
    @Singleton
    public EmployeePositionSparkProxyRestController employeePositionSparkProxyRestController(
            final EmployeePositionController<EmployeePositionDto, EmployeePositionView> positionController,
            final JsonUtil jsonUtil) {
        return new EmployeePositionSparkProxyRestController(positionController, jsonUtil);
    }

    @Provides
    @Singleton
    public SparkExceptionHandler sparkExceptionHandler(final JsonUtil jsonUtil) {
        return new SparkExceptionHandler(jsonUtil);
    }

    @Provides
    @Singleton
    public EmployeePositionController<EmployeePositionDto, EmployeePositionView> employeePositionController(
            final EmployeePositionService employeePositionService,
            final EmployeePositionAllSideDomainConverter converter) {
        return new EmployeePositionRestController(employeePositionService, converter);
    }

    @Provides
    @Singleton
    public EmployeeController<EmployeeDto, EmployeeView> employeeController(final EmployeeService employeeService,
                                                                            final EmployeeAllSideDomainConverter converter) {
        return new EmployeeRestController(employeeService, converter);
    }

    @Override
    protected void configure() {
        super.install(new ServiceModule());
        super.install(new UtilModule());
    }
}