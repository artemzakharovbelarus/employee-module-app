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

    private static final String ENV_DATASOURCE_URL_KEY = "SPRING_DATASOURCE_URL";
    private static final String ENV_DATASOURCE_USERNAME_KEY = "POSTGRES_USER";
    private static final String ENV_DATASOURCE_PASSWORD_KEY = "POSTGRES_PASSWORD";

    @Provides
    @Singleton
    public Configuration provideHibernateConfiguration() {
        final var configuration = new Configuration();

        configuration.setProperty(JDBC_URL_KEY, System.getenv(ENV_DATASOURCE_URL_KEY));
        configuration.setProperty(JDBC_USERNAME_KEY, System.getenv(ENV_DATASOURCE_USERNAME_KEY));
        configuration.setProperty(JDBC_PASSWORD_KEY, System.getenv(ENV_DATASOURCE_PASSWORD_KEY));

        return configuration.configure();
    }

    @Provides
    public Session provideEntityManager(final Configuration configuration) {
        return configuration.buildSessionFactory().openSession();
    }

    @Override
    protected void configure() {
        bindHibernateRepositories();
    }

    private void bindHibernateRepositories() {
        super.bind(EmployeeRepository.class).to(EmployeeHibernateRepository.class);
        super.bind(EmployeePositionRepository.class).to(EmployeePositionHibernateRepository.class);
    }
}