package com.azakharov.employeeapp.repository.spring.data;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.azakharov.employeeapp.repository.spring.data.proxy.EmployeePositionSpringDataProxyRepository;
import com.azakharov.employeeapp.repository.spring.data.proxy.EmployeeSpringDataProxyRepository;
import com.google.inject.AbstractModule;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDataModule extends AbstractModule {

    @Override
    protected void configure() {
        bindSpringDataRepositories(new AnnotationConfigApplicationContext(SpringDataConfig.class).getBeanFactory());
    }

    private void bindSpringDataRepositories(final ListableBeanFactory beanFactory) {
        final var positionSpringDataRepository = beanFactory.getBean(EmployeePositionSpringDataRepository.class);
        final var employeeSpringDataRepository = beanFactory.getBean(EmployeeSpringDataRepository.class);

        super.bind(EmployeePositionRepository.class)
             .toInstance(new EmployeePositionSpringDataProxyRepository(positionSpringDataRepository));
        super.bind(EmployeeRepository.class)
             .toInstance(new EmployeeSpringDataProxyRepository(employeeSpringDataRepository));
    }
}
