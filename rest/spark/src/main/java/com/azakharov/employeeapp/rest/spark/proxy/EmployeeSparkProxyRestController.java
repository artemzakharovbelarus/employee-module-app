package com.azakharov.employeeapp.rest.spark.proxy;

import com.azakharov.employeeapp.api.EmployeeController;
import com.azakharov.employeeapp.rest.dto.EmployeeDto;
import com.azakharov.employeeapp.rest.util.JsonUtil;
import com.azakharov.employeeapp.rest.view.EmployeeView;
import spark.Spark;

import javax.inject.Inject;

public class EmployeeSparkProxyRestController extends BaseSparkRestController<EmployeeDto, EmployeeView> {

    private static final String API_VERSION = "/api/v1.0";

    private static final String GET_EMPLOYEE_ENDPOINT = API_VERSION + "/employee/" + ID_ENDPOINT_PARAM;
    private static final String GET_ALL_EMPLOYEES_ENDPOINT = API_VERSION + "/employee";
    private static final String SAVE_EMPLOYEE_ENDPOINT = API_VERSION + "/employee/save";
    private static final String UPDATE_EMPLOYEE_ENDPOINT = API_VERSION + "/employee/update";
    private static final String DELETE_EMPLOYEE_ENDPOINT = API_VERSION + "/employee/delete/" + ID_ENDPOINT_PARAM;

    private final EmployeeController<EmployeeDto, EmployeeView> employeeController;

    @Inject
    public EmployeeSparkProxyRestController(final EmployeeController<EmployeeDto, EmployeeView> employeeController,
                                            final JsonUtil jsonUtil) {
        super(jsonUtil);
        this.employeeController = employeeController;
    }

    public void getEmployee() {
        Spark.get(GET_EMPLOYEE_ENDPOINT, performGetViewEndpoint(employeeController::get));
    }

    public void getAllEmployees() {
        Spark.get(GET_ALL_EMPLOYEES_ENDPOINT, performGetAllViewsEndpoint(employeeController::getAll));
    }

    public void save() {
        Spark.post(SAVE_EMPLOYEE_ENDPOINT, performUpsertDomainEndpoint(employeeController::save, EmployeeDto.class));
    }

    public void update() {
        Spark.put(UPDATE_EMPLOYEE_ENDPOINT, performUpsertDomainEndpoint(employeeController::update, EmployeeDto.class));
    }

    public void delete() {
        Spark.delete(DELETE_EMPLOYEE_ENDPOINT, performDeleteDomainEndpoint(employeeController::delete, "Employee"));
    }
}
