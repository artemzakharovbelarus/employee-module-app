package com.azakharov.employeeapp.repository.hibernate;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateModule extends AbstractModule {

    private static final String JDBC_URL_KEY = "hibernate.connection.url";
    private static final String JDBC_USERNAME_KEY = "hibernate.connection.username";
    private static final String JDBC_PASSWORD_KEY = "hibernate.connection.password";

    private static final String ENV_DATASOURCE_URL = System.getenv("SPRING_DATASOURCE_URL");
    private static final String ENV_DATASOURCE_USERNAME = System.getenv("POSTGRES_USER");
    private static final String ENV_DATASOURCE_PASSWORD = System.getenv("POSTGRES_PASSWORD");

    @Provides
    @Singleton
    public Configuration provideHibernateConfiguration() {
        final var configuration = new Configuration();

        configuration.setProperty(JDBC_URL_KEY, ENV_DATASOURCE_URL);
        configuration.setProperty(JDBC_USERNAME_KEY, ENV_DATASOURCE_USERNAME);
        configuration.setProperty(JDBC_PASSWORD_KEY, ENV_DATASOURCE_PASSWORD);

        return configuration.configure();
    }

    @Provides
    public Session provideEntityManager(final Configuration configuration) {
        return configuration.buildSessionFactory().openSession();
    }

    @Provides
    @Singleton
    public EmployeeRepository provideEmployeeRepository(final Session session) {
        return new EmployeeHibernateRepository(session);
    }

    @Provides
    @Singleton
    public EmployeePositionRepository provideEmployeePositionRepository(final Session session) {
        return new EmployeePositionHibernateRepository(session);
    }
}