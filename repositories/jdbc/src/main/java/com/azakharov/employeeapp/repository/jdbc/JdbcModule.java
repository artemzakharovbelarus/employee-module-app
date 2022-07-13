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

    private static final String ENV_DATASOURCE_URL = System.getenv("SPRING_DATASOURCE_URL");
    private static final String ENV_DATASOURCE_USERNAME = System.getenv("POSTGRES_USER");
    private static final String ENV_DATASOURCE_PASSWORD = System.getenv("POSTGRES_PASSWORD");

    @Provides
    @Singleton
    public HikariConfig provideHikariConfig() {
        final var hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(ENV_DATASOURCE_URL);
        hikariConfig.setUsername(ENV_DATASOURCE_USERNAME);
        hikariConfig.setPassword(ENV_DATASOURCE_PASSWORD);

        return hikariConfig;
    }

    @Provides
    @Singleton
    public DataSource provideDataSource(final HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Override
    protected void configure() {
        bindJdbcRepositories();
    }

    private void bindJdbcRepositories() {
        super.bind(EmployeePositionRepository.class).to(EmployeePositionJdbcRepository.class);
        super.bind(EmployeeRepository.class).to(EmployeeJdbcRepository.class);
    }
}
