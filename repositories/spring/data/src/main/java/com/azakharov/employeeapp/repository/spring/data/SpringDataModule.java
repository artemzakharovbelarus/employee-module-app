package com.azakharov.employeeapp.repository.spring.data;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.azakharov.employeeapp.repository.spring.data.proxy.EmployeePositionSpringDataProxyRepository;
import com.azakharov.employeeapp.repository.spring.data.proxy.EmployeeSpringDataProxyRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDataModule extends AbstractModule {

    @Provides
    @Singleton
    public EmployeePositionRepository provideEmployeePositionRepository() {
        final var positionSpringDataRepository = provideBeanFactory().getBean(EmployeePositionSpringDataRepository.class);
        return new EmployeePositionSpringDataProxyRepository(positionSpringDataRepository);
    }

    @Provides
    @Singleton
    public EmployeeRepository provideEmployeeRepository() {
        final var employeeSpringDataRepository = provideBeanFactory().getBean(EmployeeSpringDataRepository.class);
        return new EmployeeSpringDataProxyRepository(employeeSpringDataRepository);
    }

    private BeanFactory provideBeanFactory() {
        return new AnnotationConfigApplicationContext(SpringDataConfig.class).getBeanFactory();
    }
}
