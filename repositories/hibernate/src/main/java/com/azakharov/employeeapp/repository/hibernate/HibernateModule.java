package com.azakharov.employeeapp.repository.hibernate;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateModule extends AbstractModule {

    @Override
    protected void configure() {
        bindHibernateRepositories();
    }

    @Provides
    @Singleton
    public Configuration provideHibernateConfiguration() {
        return new Configuration().configure("hibernate.cfg.xml");
    }

    @Provides
    public Session provideEntityManager(final Configuration configuration) {
        return configuration.buildSessionFactory().openSession();
    }

    private void bindHibernateRepositories() {
        super.bind(EmployeeRepository.class).to(EmployeeHibernateRepository.class);
        super.bind(EmployeePositionRepository.class).to(EmployeePositionHibernateRepository.class);
    }
}