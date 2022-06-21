package com.azakharov.employeeapp.api;

import java.util.List;

public interface CrudController<DTO, V> {

    V get(Long id);
    List<V> getAll();
    V save(DTO dto);
    V update(DTO dto);
    void delete(Long id);
}
