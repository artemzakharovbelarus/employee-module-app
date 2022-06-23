package com.azakharov.employeeapp.repository.jdbc;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
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

public class EmployeePositionJdbcRepository extends BaseJdbcRepository<EmployeePositionEntity, Long>
        implements EmployeePositionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePositionJdbcRepository.class);

    /** SQL queries **/
    private static final String FIND_EMPLOYEE_POSITION_BY_ID_SQL = "SELECT id, name FROM employee_positions WHERE ID = ?";
    private static final String FIND_ALL_EMPLOYEE_POSITIONS_SQL = "SELECT id, name FROM employee_positions";
    private static final String SAVE_EMPLOYEE_POSITION_SQL = "INSERT INTO employee_positions (name) VALUES (?)";
    private static final String UPDATE_EMPLOYEE_POSITION_SQL = "UPDATE employee_positions SET name = ? WHERE id = ?";
    private static final String DELETE_EMPLOYEE_POSITION_SQL = "DELETE FROM employee_positions WHERE id = ?";

    /** employee_positions column names **/
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    @Inject
    public EmployeePositionJdbcRepository(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<EmployeePositionEntity> find(Long id) {
        return super.find(FIND_EMPLOYEE_POSITION_BY_ID_SQL, id);
    }

    @Override
    public List<EmployeePositionEntity> findAll() {
        return super.findAll(FIND_ALL_EMPLOYEE_POSITIONS_SQL);
    }

    @Override
    public EmployeePositionEntity save(EmployeePositionEntity entity) {
        return super.save(SAVE_EMPLOYEE_POSITION_SQL, entity);
    }

    @Override
    public EmployeePositionEntity update(EmployeePositionEntity entity) {
        return super.update(UPDATE_EMPLOYEE_POSITION_SQL, entity);
    }

    @Override
    public void delete(Long id) {
        super.delete(DELETE_EMPLOYEE_POSITION_SQL, id);
    }

    @Override
    protected List<Object> convertEntityToParams(EmployeePositionEntity entity) {
        final var params = new ArrayList<>();

        params.add(entity.getName());
        if (entity.getId() != null) {
            params.add(entity.getId());
        }

        return params;
    }

    @Override
    protected EmployeePositionEntity constructEntity(ResultSet resultSet) {
        try {
            return new EmployeePositionEntity(resultSet.getLong(ID_COLUMN), resultSet.getString(NAME_COLUMN));
        } catch (final SQLException e) {
            LOGGER.error("Exception during extracting data from JDBC result set, message: {}", e.getMessage());
            LOGGER.debug("Exception during extracting data from JDBC result set", e);
            throw new JdbcRepositoryException("Exception during extracting data from JDBC result set, message: {0}", e.getMessage());
        }
    }

    @Override
    protected EmployeePositionEntity constructSavedEntity(ResultSet generatedKeys, EmployeePositionEntity saved) {
        try {
            final var id = generatedKeys.getInt(ID_COLUMN);
            return new EmployeePositionEntity((long) id, saved.getName());
        } catch (final SQLException e) {
            LOGGER.error("Exception during extracting data from JDBC result set, message: {}", e.getMessage());
            LOGGER.debug("Exception during extracting data from JDBC result set", e);
            throw new JdbcRepositoryException("Exception during extracting data from JDBC result set, message: {0}", e.getMessage());
        }
    }
}
