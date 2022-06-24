package com.azakharov.employeeapp.rest.util.converter;

public interface AllSideDomainConverter<DTO, D, V> {

    D convertToDomain(DTO dto);
    V convertToView(D domain);
}