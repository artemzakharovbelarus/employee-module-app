package com.azakharov.employeeapp.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<D, ID> {

    Optional<D> find(ID id);
    List<D> findAll();
    D save(D domain);
    D update(D domain);
    void delete(ID id);
}
