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

    private static final String ENV_DATASOURCE_URL = System.getenv("SPRING_DATASOURCE_URL");
    private static final String ENV_DATASOURCE_USERNAME = System.getenv("POSTGRES_USER");
    private static final String ENV_DATASOURCE_PASSWORD = System.getenv("POSTGRES_PASSWORD");

    @Provides
    public EntityManagerFactory provideEntityManagerFactory() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, provideDataSource());
    }

    @Provides
    @Singleton
    public EntityManager provideEntityManager(final EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Provides
    @Singleton
    public EmployeePositionRepository provideEmployeePositionRepository(final EntityManager entityManager) {
        return new EmployeePositionEclipseLinkRepository(entityManager);
    }

    @Provides
    @Singleton
    public EmployeeRepository provideEmployeeRepository(final EntityManager entityManager) {
        return new EmployeeEclipseLinkRepository(entityManager);
    }

    private Map<String, String> provideDataSource() {
        final var dataSource = new HashMap<String, String>();

        dataSource.put(JDBC_URL_KEY, ENV_DATASOURCE_URL);
        dataSource.put(JDBC_USERNAME_KEY, ENV_DATASOURCE_USERNAME);
        dataSource.put(JDBC_PASSWORD_KEY, ENV_DATASOURCE_PASSWORD);

        return dataSource;
    }
}
