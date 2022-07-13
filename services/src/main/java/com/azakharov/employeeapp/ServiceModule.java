package com.azakharov.employeeapp;

import com.azakharov.employeeapp.repository.eclipselink.EclipseLinkModule;
import com.azakharov.employeeapp.service.email.EmailService;
import com.azakharov.employeeapp.service.email.EmailServiceImpl;
import com.azakharov.employeeapp.service.employee.EmployeeService;
import com.azakharov.employeeapp.service.employee.EmployeeServiceImpl;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionService;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionServiceImpl;
import com.azakharov.employeeapp.util.converter.EmployeeBidirectionalDomainConverter;
import com.azakharov.employeeapp.util.converter.EmployeePositionBidirectionalDomainConverter;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.inject.Named;
import java.util.Properties;

public class ServiceModule extends AbstractModule {

    private static final String MAIL_CONFIG_PROTOCOL_KEY = "mail.transport.protocol";
    private static final String MAIL_CONFIG_AUTH_KEY = "mail.smtps.auth";
    private static final String MAIL_CONFIG_HOST_KEY = "mail.smtps.host";
    private static final String MAIL_CONFIG_USER_KEY = "mail.smtps.user";
    private static final String MAIL_CONFIG_USER_PASSWORD_KEY = "mail.smtps.password";

    private static final String EMAIL_PROTOCOL = System.getenv("MAIL_TRANSPORT_PROTOCOL");
    private static final String EMAIL_AUTH = System.getenv("MAIL_SMTPS_AUTH");
    private static final String EMAIL_HOST = System.getenv("MAIL_SMTPS_HOST");
    private static final String EMAIL_SERVER_USER = System.getenv("MAIL_SMTPS_USER");
    private static final String EMAIL_SERVER_PASSWORD = System.getenv("MAIL_SMTPS_PASSWORD");

    private static final String ADMIN_EMAIL = System.getenv("ADMIN_EMAIL");

    @Provides
    @Singleton
    @Named("admin-email")
    public String provideAdminEmail() {
        return ADMIN_EMAIL;
    }

    @Provides
    @Singleton
    public Properties provideEmailProperties() {
        final var properties = new Properties();

        properties.setProperty(MAIL_CONFIG_PROTOCOL_KEY, EMAIL_PROTOCOL);
        properties.setProperty(MAIL_CONFIG_AUTH_KEY, EMAIL_AUTH);
        properties.setProperty(MAIL_CONFIG_HOST_KEY, EMAIL_HOST);
        properties.setProperty(MAIL_CONFIG_USER_KEY, EMAIL_SERVER_USER);
        properties.setProperty(MAIL_CONFIG_USER_PASSWORD_KEY, EMAIL_SERVER_PASSWORD);

        return properties;
    }

    @Override
    protected void configure() {
        super.install(new EclipseLinkModule());
//        super.install(new SpringDataModule());
//        super.install(new SpringJdbcModule());
//        super.install(new JdbcModule());
//        super.install(new HibernateModule());

        bindDomainConverters();
        bindServices();
    }

    private void bindDomainConverters() {
        super.bind(EmployeeBidirectionalDomainConverter.class);
        super.bind(EmployeePositionBidirectionalDomainConverter.class);
    }

    private void bindServices() {
        super.bind(EmployeePositionService.class).to(EmployeePositionServiceImpl.class);
        super.bind(EmployeeService.class).to(EmployeeServiceImpl.class);
        super.bind(EmailService.class).to(EmailServiceImpl.class);
    }
}
