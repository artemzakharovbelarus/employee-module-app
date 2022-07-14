package com.azakharov.employeeapp.service.messagebroker;

import com.azakharov.employeeapp.service.messagebroker.dto.EmployeeMessageDto;
import com.azakharov.employeeapp.util.json.json.JsonUtil;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitmqMessageBroker implements MessageBroker {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqMessageBroker.class);

    private final ConnectionFactory connectionFactory;
    private final String rabbitmqUrl;
    private final String rabbitmqEmailQueue;
    private final JsonUtil jsonUtil;

    @Inject
    public RabbitmqMessageBroker(final ConnectionFactory connectionFactory,
                                 final @Named("rabbitmq-url") String rabbitmqUrl,
                                 final @Named("rabbitmq-email-queue") String rabbitmqEmailQueue,
                                 final JsonUtil jsonUtil) {
        this.connectionFactory = connectionFactory;
        this.rabbitmqUrl = rabbitmqUrl;
        this.rabbitmqEmailQueue = rabbitmqEmailQueue;
        this.jsonUtil = jsonUtil;
    }

    @Override
    public void sendEmailMessage(final EmployeeMessageDto messageDto) {
        LOGGER.debug("Sending email RabbitMQ message started");
        LOGGER.trace("EmployeeMessageDto parameter: {}", messageDto);

        final var amqpMessage = jsonUtil.write(messageDto);
        sendRabbitmqMessage(amqpMessage.getBytes(StandardCharsets.UTF_8));
    }

    private void sendRabbitmqMessage(final byte[] message) {
        try (var connection = connectionFactory.newConnection(rabbitmqUrl);
             var channel = connection.createChannel()) {

            channel.queueDeclare(rabbitmqEmailQueue, false, false, false, null);
            channel.basicPublish(StringUtils.EMPTY, rabbitmqEmailQueue, null, message);
        } catch (final IOException | TimeoutException e) {
            LOGGER.error("Exception during sending RabbitMQ message, message: {}", e.getMessage());
            LOGGER.error("Exception during sending RabbitMQ message", e);
        }
    }
}
