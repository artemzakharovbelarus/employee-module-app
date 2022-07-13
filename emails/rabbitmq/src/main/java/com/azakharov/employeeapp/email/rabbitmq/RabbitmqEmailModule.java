package com.azakharov.employeeapp.email.rabbitmq;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitmqEmailModule extends AbstractModule {

    private static final String RABBITMQ_HOST = System.getenv("RABBITMQ_HOST");
    private static final int RABBITMQ_PORT = Integer.parseInt(System.getenv("RABBITMQ_PORT"));
    private static final String RABBITMQ_USERNAME = System.getenv("RABBITMQ_DEFAULT_USER");
    private static final String RABBITMQ_PASSWORD = System.getenv("RABBITMQ_DEFAULT_PASSWORD");

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

    @Override
    protected void configure() {
        super.bind(EmailRabbitmqListener.class);
    }
}
