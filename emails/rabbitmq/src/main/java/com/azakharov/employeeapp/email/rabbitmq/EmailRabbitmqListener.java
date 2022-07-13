package com.azakharov.employeeapp.email.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class EmailRabbitmqListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailRabbitmqListener.class);

    private static final String RABBITMQ_URL = System.getenv("RABBITMQ_URL");
    private static final String RABBITMQ_EMAIL_QUEUE = System.getenv("RABBITMQ_EMAIL_QUEUE");

    private final ConnectionFactory connectionFactory;

    @Inject
    public EmailRabbitmqListener(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void receiveMessage() {
        try (var connection = connectionFactory.newConnection(RABBITMQ_URL);
             var channel = connection.createChannel()) {

            channel.basicConsume(RABBITMQ_EMAIL_QUEUE, true, performDeliverCallback(), consumerTag -> {});
        } catch (final IOException | TimeoutException e) {
            LOGGER.error("Exception during receiving Rabbit MQ message, message: {}", e.getMessage());
            LOGGER.debug("Exception during receiving Rabbit MQ message", e);
            throw new RuntimeException(e);
        }
    }

    private DeliverCallback performDeliverCallback() {
        return (consumerTag, amqpMessage) -> {
            final var message = new String(amqpMessage.getBody(), StandardCharsets.UTF_8);
            LOGGER.debug("Rabbit MQ message received, message: {}", message);
        };
    }
}
