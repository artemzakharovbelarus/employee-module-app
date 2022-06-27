package com.azakharov.employeeapp.repository.spring.jdbc;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class SpringJdbcModule extends AbstractModule {

    @Provides
    @Singleton HikariConfig hikariConfig() {
        return new HikariConfig("repositories/spring/jdbc/src/main/resources/spring-jdbc.properties");
    }

    @Provides
    @Singleton
    public DataSource dataSource(final HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Provides
    @Singleton
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Override
    protected void configure() {
        bindSpringJdbcRepositories();
    }

    private void bindSpringJdbcRepositories() {
        super.bind(EmployeePositionRepository.class).to(EmployeePositionSpringJdbcRepository.class);
        super.bind(EmployeeRepository.class).to(EmployeeSpringJdbcRepository.class);
    }
}
