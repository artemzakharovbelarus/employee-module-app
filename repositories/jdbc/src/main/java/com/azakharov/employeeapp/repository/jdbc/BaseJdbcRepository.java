package com.azakharov.employeeapp.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseJdbcRepository<E, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseJdbcRepository.class);

    protected final DataSource dataSource;

    public BaseJdbcRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Optional<E> find(final String sql, final ID id) {
        return processSql(sql, List.of(id), performResultSetActionForFind());
    }

    protected List<E> findAll(final String sql) {
        final var entities = new ArrayList<E>();

        processSql(sql, List.of(), preparedStatement -> {
            performConsumerActionForFindAll(entities).accept(preparedStatement);
            return Optional.empty();
        });

        return entities;
    }

    protected E save(final String sql, final E entity) {
        return processSql(sql,
                          convertEntityToParams(entity),
                          performResultSetActionForSave(entity),
                          Statement.RETURN_GENERATED_KEYS).orElseThrow();
    }

    @SuppressWarnings("UNCHECKED_CAST")
    protected E update(final String sql, final E entity) {
        final var params = convertEntityToParams(entity);
        processSql(sql, params, preparedStatement -> {
            performActionForUpdating((ID) params.get(params.size() - 1)).accept(preparedStatement);
            return Optional.empty();
        });

        return entity;
    }

    protected void delete(final String sql, final ID id) {
        processSql(sql, List.of(id), preparedStatement -> {
            performActionForUpdating(id).accept(preparedStatement);
            return Optional.empty();
        });
    }

    protected <R> Optional<R> processSql(final String sql,
                                         final List<Object> params,
                                         final Function<PreparedStatement, R> resultSetExecutor) {
        return processSql(sql, params, resultSetExecutor, Statement.NO_GENERATED_KEYS);
    }

    protected <R> Optional<R> processSql(final String sql,
                                         final List<Object> params,
                                         final Function<PreparedStatement, R> resultSetExecutor,
                                         final int statementKey) {
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql, statementKey)) {

            setParams(preparedStatement, params);
            return Optional.ofNullable(resultSetExecutor.apply(preparedStatement));
        } catch (final SQLException e) {
            LOGGER.error("Exception during processing SQL query, message: {}", e.getMessage());
            LOGGER.debug("Exception during processing SQL query", e);
            throw new JdbcRepositoryException("Exception during processing SQL query, message: {0}", e.getMessage());
        }
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
    protected abstract E constructEntity(final ResultSet resultSet);

    /**
     * Constructs entity from generated database id and saved entity.
     *
     * @param generatedKeys generated database id.
     * @param saved entity, that was saved in database.
     * @return constructed entity with generated id.
     */
    protected abstract E constructSavedEntity(final ResultSet generatedKeys, final E saved);

    private Function<PreparedStatement, E> performResultSetActionForFind() {
        return preparedStatement -> {

            try (var resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? constructEntity(resultSet) : null;
            } catch (final SQLException e) {
                LOGGER.error("Exception during performing JDBC ResultSet for findById, message: {}", e.getMessage());
                LOGGER.debug("Exception during performing JDBC ResultSet for findById action", e);
                throw new JdbcRepositoryException("Exception during performing JDBC ResultSet for findById, message: {0}", e.getMessage());
            }

        };
    }

    private Consumer<PreparedStatement> performConsumerActionForFindAll(final List<E> resultList) {
        return preparedStatement -> {

            try (var resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    resultList.add(constructEntity(resultSet));
                }

            } catch (final SQLException e) {
                LOGGER.error("Exception during performing JDBC ResultSet for findAll, message: {}", e.getMessage());
                LOGGER.debug("Exception during performing JDBC ResultSet for findAll", e);
                throw new JdbcRepositoryException("Exception during performing JDBC ResultSet for findAll, message: {0}", e.getMessage());
            }

        };
    }

    private Function<PreparedStatement, E> performResultSetActionForSave(final E entity) {
        return preparedStatement -> {

            try {
                preparedStatement.executeUpdate();

                try (var resultSet = preparedStatement.getGeneratedKeys()) {
                    return resultSet.next() ? constructSavedEntity(resultSet, entity) : null;
                }

            } catch (final SQLException e) {
                LOGGER.error("Exception during performing JDBC ResultSet for save, message: {}", e.getMessage());
                LOGGER.debug("Exception during performing JDBC ResultSet for save", e);
                throw new JdbcRepositoryException("Exception during performing JDBC ResultSet for save, message: {0}", e.getMessage());
            }
        };
    }

    private Consumer<PreparedStatement> performActionForUpdating(final ID id) {
        return preparedStatement -> {

            try {
                final var rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected == 0) {
                    throw new JdbcRepositoryException("There is no record with ID {0}", id);
                }

            } catch (final SQLException e) {
                LOGGER.error("Exception during executing JDBC preparedStatement for update, message: {}", e.getMessage());
                LOGGER.debug("Exception during executing JDBC preparedStatement for update", e);
                throw new JdbcRepositoryException("Exception during executing JDBC preparedStatement for update, message: {0}", e.getMessage());
            }

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
            throw new JdbcRepositoryException("Exception during setting params to JDBC prepared statement, message: {0}", e.getMessage());
        }
    }
}