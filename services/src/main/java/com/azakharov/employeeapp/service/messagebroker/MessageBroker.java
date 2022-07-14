package com.azakharov.employeeapp.service.messagebroker;

import com.azakharov.employeeapp.service.messagebroker.dto.EmployeeMessageDto;

public interface MessageBroker {

    void sendEmailMessage(final EmployeeMessageDto messageDto);
}
