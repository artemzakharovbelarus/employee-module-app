package com.azakharov.employeeapp.repository.jdbc;

import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeeEntity;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeePositionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeJdbcRepository extends BaseJdbcRepository<EmployeeEntity, Long> implements EmployeeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeJdbcRepository.class);

    /** SQL queries **/
    private static final String FIND_EMPLOYEE_BY_ID_SQL = "SELECT e.id, e.first_name, e.surname, p.id AS \"p.ID\", " +
                                                          "p.name AS \"p.name\" FROM employees e " +
                                                          "INNER JOIN employee_positions p ON e.position_id = p.id " +
                                                          "WHERE e.id = ?";
    private static final String FIND_ALL_EMPLOYEES_SQL = "SELECT e.id, e.first_name, e.surname, p.id AS \"p.id\", " +
                                                         "p.name AS \"p.name\" FROM employees e " +
                                                         "INNER JOIN employee_positions p ON e.position_id = p.id";
    private static final String SAVE_EMPLOYEE_SQL = "INSERT INTO employees (first_name, surname, position_id)" +
                                                    "VALUES (?, ?, ?)";
    private static final String UPDATE_EMPLOYEE_SQL = "UPDATE employees SET first_name = ?, surname = ?, position_id = ? " +
                                                      "WHERE id = ?";
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employees WHERE id = ?";

    /** employees and employee_positions column names **/
    private static final String ID_COLUMN = "id";
    private static final String POSITION_ID_COLUMN = "p." + ID_COLUMN;
    private static final String EMPLOYEE_FIRST_NAME_COLUMN = "first_name";
    private static final String EMPLOYEE_SURNAME_COLUMN = "surname";
    private static final String EMPLOYEE_POSITION_NAME_COLUMN = "p.name";

    @Inject
    public EmployeeJdbcRepository(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<EmployeeEntity> find(final Long id) {
        LOGGER.debug("Finding EmployeeEntity in database started for id: {}", id);
        final var employee = super.find(FIND_EMPLOYEE_BY_ID_SQL, id);
        LOGGER.trace("EmployeeEntity detailed printing: {}", employee);

        return employee;
    }

    @Override
    public List<EmployeeEntity> findAll() {
        LOGGER.debug("Finding all EmployeeEntity in database started");
        final var employees = super.findAll(FIND_ALL_EMPLOYEES_SQL);
        LOGGER.trace("EmployeeEntity detailed printing: {}", employees);

        return employees;
    }

    @Override
    public EmployeeEntity save(final EmployeeEntity employee) {
        LOGGER.debug("EmployeeEntity saving started, position: {}", employee);
        final var saved = super.save(SAVE_EMPLOYEE_SQL, employee);
        LOGGER.debug("EmployeeEntity saving successfully ended, generated id: {}", saved.getId());

        return saved;
    }

    @Override
    public EmployeeEntity update(final EmployeeEntity employee) {
        LOGGER.debug("EmployeeEntity updating started, position: {}", employee);
        return super.update(UPDATE_EMPLOYEE_SQL, employee);
    }

    @Override
    public void delete(final Long id) {
        LOGGER.debug("EmployeeEntity deleting started, id: {}", id);
        super.delete(DELETE_EMPLOYEE_SQL, id);
    }

    @Override
    protected List<Object> convertEntityToParams(final EmployeeEntity employee) {
        final var params = new ArrayList<>();
        params.add(employee.getFirstName());
        params.add(employee.getSurname());
        params.add(employee.getPositionEntity().getId());

        if (employee.getId() != null) {
            params.add(employee.getId());
        }

        return params;
    }

    @Override
    protected EmployeeEntity constructEntity(final ResultSet resultSet) {
        try {
            final var position = new EmployeePositionEntity(resultSet.getLong(POSITION_ID_COLUMN),
                                                            resultSet.getString(EMPLOYEE_POSITION_NAME_COLUMN));
            return new EmployeeEntity(resultSet.getLong(ID_COLUMN),
                                      resultSet.getString(EMPLOYEE_FIRST_NAME_COLUMN),
                                      resultSet.getString(EMPLOYEE_SURNAME_COLUMN),
                                      position);
        } catch (final SQLException e) {
            LOGGER.error("Exception during extracting data from JDBC result set, message: {}", e.getMessage());
            LOGGER.debug("Exception during extracting data from JDBC result set", e);
            throw new JdbcRepositoryException("Exception during extracting data from JDBC result set, message: {0}", e.getMessage());
        }
    }

    @Override
    protected EmployeeEntity constructSavedEntity(final ResultSet generatedKeys, final EmployeeEntity saved) {
        try {
            final var id = generatedKeys.getInt(ID_COLUMN);
            return new EmployeeEntity((long) id, saved.getFirstName(), saved.getSurname(), saved.getPositionEntity());
        } catch (final SQLException e) {
            LOGGER.error("Exception during extracting data from JDBC result set, message: {}", e.getMessage());
            LOGGER.debug("Exception during extracting data from JDBC result set", e);
            throw new JdbcRepositoryException("Exception during extracting data from JDBC result set, message: {0}", e.getMessage());
        }
    }
}
