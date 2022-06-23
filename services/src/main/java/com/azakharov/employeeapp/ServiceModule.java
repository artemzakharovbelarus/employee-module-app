package com.azakharov.employeeapp;

import com.azakharov.employeeapp.repository.jdbc.JdbcModule;
import com.azakharov.employeeapp.util.converter.EmployeeBidirectionalDomainConverter;
import com.azakharov.employeeapp.util.converter.EmployeePositionBidirectionalDomainConverter;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.install(new JdbcModule());
//        super.install(new HibernateModule());

        bindDomainConverters();
    }

    private void bindDomainConverters() {
        super.bind(EmployeeBidirectionalDomainConverter.class);
        super.bind(EmployeePositionBidirectionalDomainConverter.class);
    }
}