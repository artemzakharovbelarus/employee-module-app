package com.azakharov.employeeapp.repository.eclipselink;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EclipseLinkModule extends AbstractModule {

    private static final String PERSISTENCE_UNIT_NAME = "postgres-pu";

    private static final String JDBC_URL_KEY = "javax.persistence.jdbc.url";
    private static final String JDBC_USERNAME_KEY = "javax.persistence.jdbc.user";
    private static final String JDBC_PASSWORD_KEY = "javax.persistence.jdbc.password";

    private static final String ENV_DATASOURCE_URL_KEY = "SPRING_DATASOURCE_URL";
    private static final String ENV_DATASOURCE_USERNAME_KEY = "POSTGRES_USER";
    private static final String ENV_DATASOURCE_PASSWORD_KEY = "POSTGRES_PASSWORD";

    @Provides
    public EntityManagerFactory provideEntityManagerFactory() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, provideDataSource());
    }

    @Provides
    @Singleton
    public EntityManager provideEntityManager(final EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    protected void configure() {
        bindEclipseLinkRepositories();
    }

    private void bindEclipseLinkRepositories() {
        super.bind(EmployeePositionRepository.class).to(EmployeePositionEclipseLinkRepository.class);
        super.bind(EmployeeRepository.class).to(EmployeeEclipseLinkRepository.class);
    }

    private Map<String, String> provideDataSource() {
        final var dataSource = new HashMap<String, String>();

        dataSource.put(JDBC_URL_KEY, System.getenv(ENV_DATASOURCE_URL_KEY));
        dataSource.put(JDBC_USERNAME_KEY, System.getenv(ENV_DATASOURCE_USERNAME_KEY));
        dataSource.put(JDBC_PASSWORD_KEY, System.getenv(ENV_DATASOURCE_PASSWORD_KEY));

        return dataSource;
    }
}
