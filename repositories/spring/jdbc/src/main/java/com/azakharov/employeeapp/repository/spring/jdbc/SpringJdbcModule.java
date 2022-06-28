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

    private static final String ENV_DATASOURCE_URL_KEY = "SPRING_DATASOURCE_URL";
    private static final String ENV_DATASOURCE_USERNAME_KEY = "POSTGRES_USER";
    private static final String ENV_DATASOURCE_PASSWORD_KEY = "POSTGRES_PASSWORD";

    @Provides
    @Singleton
    public HikariConfig provideHikariConfig() {
        final var hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(System.getenv(ENV_DATASOURCE_URL_KEY));
        hikariConfig.setUsername(System.getenv(ENV_DATASOURCE_USERNAME_KEY));
        hikariConfig.setPassword(System.getenv(ENV_DATASOURCE_PASSWORD_KEY));

        return hikariConfig;
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
