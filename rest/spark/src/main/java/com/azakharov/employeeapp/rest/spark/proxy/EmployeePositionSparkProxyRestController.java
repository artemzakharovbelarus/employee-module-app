package com.azakharov.employeeapp.rest.spark.proxy;

import com.azakharov.employeeapp.api.EmployeePositionController;
import com.azakharov.employeeapp.rest.dto.EmployeePositionDto;
import com.azakharov.employeeapp.util.JsonUtil;
import com.azakharov.employeeapp.rest.view.EmployeePositionView;
import spark.Spark;

import javax.inject.Inject;

public class EmployeePositionSparkProxyRestController extends BaseSparkRestController<EmployeePositionDto, EmployeePositionView> {

    private static final String API_VERSION = "/api/v1.0";

    private static final String GET_EMPLOYEE_POSITION_ENDPOINT = API_VERSION + "/employee-position/" + ID_ENDPOINT_PARAM;
    private static final String GET_ALL_EMPLOYEE_POSITIONS_ENDPOINT = API_VERSION + "/employee-position";
    private static final String SAVE_EMPLOYEE_POSITION_ENDPOINT = API_VERSION + "/employee-position/save";
    private static final String UPDATE_EMPLOYEE_POSITION_ENDPOINT = API_VERSION + "/employee-position/update";
    private static final String DELETE_EMPLOYEE_POSITION_ENDPOINT = API_VERSION + "/employee-position/delete/" + ID_ENDPOINT_PARAM;

    private final EmployeePositionController<EmployeePositionDto, EmployeePositionView> employeePositionController;

    @Inject
    public EmployeePositionSparkProxyRestController(final EmployeePositionController<EmployeePositionDto, EmployeePositionView> employeePositionController,
                                                    final JsonUtil jsonUtil) {
        super(jsonUtil);
        this.employeePositionController = employeePositionController;
    }

    public void getEmployeePosition() {
        Spark.get(GET_EMPLOYEE_POSITION_ENDPOINT, performGetViewEndpointLogic(employeePositionController::get));
    }

    public void getAllEmployeePositions() {
        Spark.get(GET_ALL_EMPLOYEE_POSITIONS_ENDPOINT, performGetAllViewsEndpointLogic(employeePositionController::getAll));
    }

    public void save() {
        Spark.post(SAVE_EMPLOYEE_POSITION_ENDPOINT,
                   performUpsertDomainEndpointLogic(employeePositionController::save,  EmployeePositionDto.class));
    }

    public void update() {
        Spark.put(UPDATE_EMPLOYEE_POSITION_ENDPOINT,
                  performUpsertDomainEndpointLogic(employeePositionController::update,  EmployeePositionDto.class));
    }

    public void delete() {
        Spark.delete(DELETE_EMPLOYEE_POSITION_ENDPOINT, performDeleteDomainEndpointLogic(employeePositionController::delete, "Position"));
    }
}
