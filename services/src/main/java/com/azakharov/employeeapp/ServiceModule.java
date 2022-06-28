package com.azakharov.employeeapp;

import com.azakharov.employeeapp.repository.eclipselink.EclipseLinkModule;
import com.azakharov.employeeapp.repository.spring.data.SpringDataModule;
import com.azakharov.employeeapp.util.converter.EmployeeBidirectionalDomainConverter;
import com.azakharov.employeeapp.util.converter.EmployeePositionBidirectionalDomainConverter;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.install(new EclipseLinkModule());
//        super.install(new SpringDataModule());
//        super.install(new SpringJdbcModule());
//        super.install(new JdbcModule());
//        super.install(new HibernateModule());

        bindDomainConverters();
    }

    private void bindDomainConverters() {
        super.bind(EmployeeBidirectionalDomainConverter.class);
        super.bind(EmployeePositionBidirectionalDomainConverter.class);
    }
}
