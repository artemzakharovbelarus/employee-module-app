package com.azakharov.employeeapp.repository.jdbc;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class JdbcModule extends AbstractModule {

    @Override
    protected void configure() {
        bindJdbcRepositories();
    }

    @Provides
    @Singleton
    public HikariConfig provideHikariConfig() {
        return new HikariConfig("repositories/jdbc/src/main/resources/jdbc.properties");
    }

    @Provides
    @Singleton
    public DataSource dataSource(final HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    private void bindJdbcRepositories() {
        super.bind(EmployeePositionRepository.class).to(EmployeePositionJdbcRepository.class);
        super.bind(EmployeeRepository.class).to(EmployeeJdbcRepository.class);
    }
}
