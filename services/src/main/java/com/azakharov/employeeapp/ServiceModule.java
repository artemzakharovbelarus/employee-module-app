package com.azakharov.employeeapp;

import com.azakharov.employeeapp.repository.eclipselink.EclipseLinkModule;
import com.azakharov.employeeapp.service.email.EmailService;
import com.azakharov.employeeapp.service.email.EmailServiceImpl;
import com.azakharov.employeeapp.service.employee.EmployeeService;
import com.azakharov.employeeapp.service.employee.EmployeeServiceImpl;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionService;
import com.azakharov.employeeapp.service.employeeposition.EmployeePositionServiceImpl;
import com.azakharov.employeeapp.service.messagebroker.MessageBroker;
import com.azakharov.employeeapp.service.messagebroker.RabbitmqMessageBroker;
import com.azakharov.employeeapp.util.converter.EmployeeBidirectionalDomainConverter;
import com.azakharov.employeeapp.util.converter.EmployeePositionBidirectionalDomainConverter;
import com.azakharov.employeeapp.util.json.UtilModule;
import com.azakharov.employeeapp.util.json.json.JsonUtil;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.rabbitmq.client.ConnectionFactory;

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

    private static final String RABBITMQ_HOST = System.getenv("RABBITMQ_HOST");
    private static final int RABBITMQ_PORT = Integer.parseInt(System.getenv("RABBITMQ_PORT"));
    private static final String RABBITMQ_USERNAME = System.getenv("RABBITMQ_DEFAULT_USER");
    private static final String RABBITMQ_PASSWORD = System.getenv("RABBITMQ_DEFAULT_PASSWORD");

    private static final String RABBITMQ_URL = System.getenv("RABBITMQ_URL");
    private static final String RABBITMQ_EMAIL_QUEUE = System.getenv("RABBITMQ_EMAIL_QUEUE");

    @Provides
    @Singleton
    public MessageBroker provideMessageBroker(final ConnectionFactory connectionFactory,
                                              final @Named("rabbitmq-url") String rabbitmqUrl,
                                              final @Named("rabbitmq-email-queue") String rabbitmqEmailQueue,
                                              final JsonUtil jsonUtil) {
        return new RabbitmqMessageBroker(connectionFactory, rabbitmqUrl, rabbitmqEmailQueue, jsonUtil);
    }

    @Provides
    @Singleton
    @Named("rabbitmq-url")
    public String provideRabbitmqUrl() {
        return RABBITMQ_URL;
    }

    @Provides
    @Singleton
    @Named("rabbitmq-email-queue")
    public String provideRabbitmqEmailQueue() {
        return RABBITMQ_EMAIL_QUEUE;
    }

    @Provides
    @Singleton
    @Named("admin-email")
    public String provideAdminEmail() {
        return ADMIN_EMAIL;
    }

    @Provides
    @Singleton
    public ConnectionFactory provideConnectionFactory() {
        final var factory = new ConnectionFactory();

        factory.setHost(RABBITMQ_HOST);
        factory.setPort(RABBITMQ_PORT);
        factory.setUsername(RABBITMQ_USERNAME);
        factory.setPassword(RABBITMQ_PASSWORD);

        return factory;
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
        super.install(new UtilModule());
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
