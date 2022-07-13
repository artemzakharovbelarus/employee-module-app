package com.azakharov.employeeapp.domain;

public class NewEmployeeEmail {

    private final String email;
    private final String subject;
    private final String text;

    public NewEmployeeEmail(final String email,
                            final String subject,
                            final String text) {
        this.email = email;
        this.subject = subject;
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "NewEmployeeEmail{" +
               "email='" + email + '\'' +
               ", subject='" + subject + '\'' +
               ", text='" + text + '\'' +
               '}';
    }
}
