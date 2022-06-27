package com.azakharov.employeeapp.repository.spring.jdbc;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeePositionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeePositionSpringJdbcRepository extends BaseSpringJdbcRepository<EmployeePositionEntity, Long>
        implements EmployeePositionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePositionSpringJdbcRepository.class);

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
    public EmployeePositionSpringJdbcRepository(final JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<EmployeePositionEntity> find(final Long id) {
        LOGGER.debug("Finding EmployeePositionEntity in database started for id: {}", id);
        final var position = super.find(FIND_EMPLOYEE_POSITION_BY_ID_SQL, id);
        LOGGER.trace("EmployeePositionEntity detailed printing: {}", position);

        return position;
    }

    @Override
    public List<EmployeePositionEntity> findAll() {
        LOGGER.debug("Finding all EmployeePositionEntity in database started");
        final var positions = super.findAll(FIND_ALL_EMPLOYEE_POSITIONS_SQL);
        LOGGER.trace("EmployeePositionEntities detailed printing: {}", positions);

        return positions;
    }

    @Override
    public EmployeePositionEntity save(final EmployeePositionEntity position) {
        LOGGER.debug("EmployeePositionEntity saving started, position: {}", position);
        final var saved = super.save(SAVE_EMPLOYEE_POSITION_SQL, position);
        LOGGER.debug("EmployeePositionEntity saving successfully ended, generated id: {}", saved.getId());

        return saved;
    }

    @Override
    public EmployeePositionEntity update(final EmployeePositionEntity position) {
        LOGGER.debug("EmployeePositionEntity updating started, position: {}", position);
        return super.update(UPDATE_EMPLOYEE_POSITION_SQL, position);
    }

    @Override
    public void delete(final Long id) {
        LOGGER.debug("EmployeePositionEntity deleting started, id: {}", id);
        super.delete(DELETE_EMPLOYEE_POSITION_SQL, id);
    }

    @Override
    protected List<Object> convertEntityToParams(final EmployeePositionEntity position) {
        final var params = new ArrayList<>();

        params.add(position.getName());
        if (position.getId() != null) {
            params.add(position.getId());
        }

        return params;
    }

    @Override
    protected EmployeePositionEntity constructEntity(final ResultSet resultSet, final int rowNum) {
        try {
            return new EmployeePositionEntity(resultSet.getLong(ID_COLUMN), resultSet.getString(NAME_COLUMN));
        } catch (final SQLException e) {
            LOGGER.error("Exception during extracting data from JDBC result set, message: {}", e.getMessage());
            LOGGER.debug("Exception during extracting data from JDBC result set", e);
            throw new SpringJdbcRepositoryException("Exception during extracting data from JDBC result set, message: {0}", e.getMessage());
        }
    }

    @Override
    protected EmployeePositionEntity constructSavedEntity(final Long id, final EmployeePositionEntity saved) {
        return new EmployeePositionEntity(id, saved.getName());
    }
}
