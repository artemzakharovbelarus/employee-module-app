package com.azakharov.employeeapp.service.email;

import com.azakharov.employeeapp.domain.NewEmployeeEmail;

public interface EmailService {

    void sendEmail(final NewEmployeeEmail email);
}
