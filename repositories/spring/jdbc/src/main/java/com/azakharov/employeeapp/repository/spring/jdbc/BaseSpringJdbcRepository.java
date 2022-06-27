package com.azakharov.employeeapp.repository.spring.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class BaseSpringJdbcRepository<E, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSpringJdbcRepository.class);

    private static final String ID_GENERATED_VALUE = "id";

    protected final JdbcTemplate jdbcTemplate;

    public BaseSpringJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected Optional<E> find(final String sql, final ID id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::constructEntity, id));
    }

    protected List<E> findAll(final String sql) {
        return jdbcTemplate.query(sql, this::constructEntity);
    }

    protected E save(final String sql, final E entity) {
        final var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(performPreparedStatementForSave(sql, entity), keyHolder);

        return Optional.ofNullable(keyHolder.getKey().longValue())
                       .map(id -> constructSavedEntity(id, entity))
                       .orElseThrow();
    }

    protected E update(final String sql, final E entity) {
        final var params = convertEntityToParams(entity);
        jdbcTemplate.update(sql, params.toArray());

        return entity;
    }

    protected void delete(final String sql, final ID id) {
        final var affectedRow = jdbcTemplate.update(sql, id);
        checkAffectedRow(id, affectedRow);
    }

    /**
     * Converts entity to list of parameters, that will be injected into SQL query.
     *
     * @param entity database entity.
     * @return list of parameters.
     */
    protected abstract List<Object> convertEntityToParams(final E entity);

    /**
     * Constructs entity from ResultSet after processing SQL query.
     *
     * @param resultSet result of SQL query.
     * @return constructed entity.
     */
    protected abstract E constructEntity(final ResultSet resultSet, int rowNum);

    /**
     * Constructs entity from generated database id and saved entity.
     *
     * @param id generated database id.
     * @param saved entity, that was saved in database.
     * @return constructed entity with generated id.
     */
    protected abstract E constructSavedEntity(final Long id, final E saved);

    private void checkAffectedRow(ID id, int affectedRow) {
        if (affectedRow == 0) {
            throw new SpringJdbcRepositoryException("There is no record with ID {0}", id);
        }
    }

    private PreparedStatementCreator performPreparedStatementForSave(String sql, E entity) {
        return con -> {

            final var preparedStatement = con.prepareStatement(sql, new String[]{ID_GENERATED_VALUE});
            setParams(preparedStatement, convertEntityToParams(entity));
            return preparedStatement;

        };
    }

    private void setParams(final PreparedStatement preparedStatement, final List<Object> params) {
        try {
            for (int index = 0; index != params.size(); index++) {
                final var sqlQueryIndex = index + 1;
                preparedStatement.setObject(sqlQueryIndex, params.get(index));
            }
        } catch (final SQLException e) {
            LOGGER.error("Exception during setting params to JDBC prepared statement, message: {}", e.getMessage());
            LOGGER.debug("Exception during setting params to JDBC prepared statement", e);
            throw new SpringJdbcRepositoryException("Exception during setting params to JDBC prepared statement, message: {0}", e.getMessage());
        }
    }
}