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

    @Provides
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, dataSource());
    }

    @Provides
    @Singleton
    public EntityManager entityManager(final EntityManagerFactory entityManagerFactory) {
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

    private Map<String, String> dataSource() {
        final var dataSource = new HashMap<String, String>();

        dataSource.put("javax.persistence.jdbc.url", System.getenv("SPRING_DATASOURCE_URL"));
        dataSource.put("javax.persistence.jdbc.user", System.getenv("POSTGRES_USER"));
        dataSource.put("javax.persistence.jdbc.password", System.getenv("POSTGRES_PASSWORD"));

        return dataSource;
    }
}
