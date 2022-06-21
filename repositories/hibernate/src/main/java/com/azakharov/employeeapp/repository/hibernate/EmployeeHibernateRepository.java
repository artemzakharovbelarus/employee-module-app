package com.azakharov.employeeapp.repository.hibernate;

import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeeEntity;
import org.hibernate.Session;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class EmployeeHibernateRepository extends BaseHibernateRepository<EmployeeEntity, Long> implements EmployeeRepository {

    @Inject
    public EmployeeHibernateRepository(final Session session) {
        super(session, EmployeeEntity.class);
    }

    @Override
    public Optional<EmployeeEntity> find(Long id) {
        return super.find(id);
    }

    @Override
    public List<EmployeeEntity> findAll() {
        return super.findAll();
    }

    @Override
    public EmployeeEntity save(EmployeeEntity employee) {
        return super.save(employee);
    }

    @Override
    public EmployeeEntity update(EmployeeEntity employee) {
        return super.update(employee);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }
}
