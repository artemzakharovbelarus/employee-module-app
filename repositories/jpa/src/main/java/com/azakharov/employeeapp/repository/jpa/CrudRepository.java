package com.azakharov.employeeapp.repository.jpa;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID> {

    Optional<E> find(ID id);
    List<E> findAll();
    E save(E entity);
    E update(E entity);
    void delete(ID id);
}
