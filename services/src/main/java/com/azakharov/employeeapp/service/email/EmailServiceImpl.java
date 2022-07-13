package com.azakharov.employeeapp.service.email;

import com.azakharov.employeeapp.domain.NewEmployeeEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private static final String MAIL_CONFIG_USER_KEY = "mail.smtps.user";
    private static final String MAIL_CONFIG_USER_PASSWORD_KEY = "mail.smtps.password";

    private final String adminEmail;
    private final Properties mailProperties;

    @Inject
    public EmailServiceImpl(final @Named("admin-email") String adminEmail,
                            final Properties mailProperties) {
        this.adminEmail = adminEmail;
        this.mailProperties = mailProperties;
    }

    @Override
    public void sendEmail(final NewEmployeeEmail email) {
        LOGGER.debug("Email sending started, message: {}", email);

        final var session = Session.getDefaultInstance(mailProperties);
        final var message = performMessage(session, email);
        processSending(session, message);
    }

    private MimeMessage performMessage(final Session session, final NewEmployeeEmail email) {
        try {
            final var message = new MimeMessage(session);

            message.setFrom(new InternetAddress(mailProperties.getProperty(MAIL_CONFIG_USER_KEY)));
            message.addRecipients(Message.RecipientType.TO, new InternetAddress[]{ new InternetAddress(adminEmail) });
            message.setSubject(email.getSubject());
            message.setText(email.getText());

            return message;
        } catch (final MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void processSending(final Session session, final MimeMessage message) {
        final var transport = performEmailTransport(session);

        try {
            transport.connect(mailProperties.getProperty(MAIL_CONFIG_USER_KEY),
                              mailProperties.getProperty(MAIL_CONFIG_USER_PASSWORD_KEY));

            transport.sendMessage(message, message.getAllRecipients());
        } catch (final MessagingException e) {
            LOGGER.error("Exception during sending email, message: {}", e.getMessage());
            LOGGER.debug("Exception during sending email", e);
        } finally {
            closeEmailTransport(transport);
        }
    }

    private Transport performEmailTransport(final Session session) {
        try {
            return session.getTransport();
        } catch (final NoSuchProviderException e) {
            LOGGER.error("Exception during getting email transport, message: {}", e.getMessage());
            LOGGER.debug("Exception during getting email transport", e);
            throw new RuntimeException(e);
        }
    }

    private void closeEmailTransport(final Transport transport) {
        try {
            transport.close();
        } catch (final MessagingException e) {
            LOGGER.error("Exception during closing email transport, message: {}", e.getMessage());
            LOGGER.debug("Exception during closing email transport", e);
        }
    }
}
