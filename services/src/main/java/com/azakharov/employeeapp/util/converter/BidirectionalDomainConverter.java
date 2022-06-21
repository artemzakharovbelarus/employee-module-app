package com.azakharov.employeeapp.util.converter;

public interface BidirectionalDomainConverter<D, E> {

    D convertToDomain(E entity);
    E convertToEntity(D domain);
}
