package com.azakharov.employeeapp.repository.hibernate;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeePositionEntity;
import org.hibernate.Session;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class EmployeePositionHibernateRepository extends BaseHibernateRepository<EmployeePositionEntity, Long>
        implements EmployeePositionRepository {

    @Inject
    public EmployeePositionHibernateRepository(final Session session) {
        super(session, EmployeePositionEntity.class);
    }

    @Override
    public Optional<EmployeePositionEntity> find(final Long id) {
        return super.find(id);
    }

    @Override
    public List<EmployeePositionEntity> findAll() {
        return super.findAll();
    }

    @Override
    public EmployeePositionEntity save(final EmployeePositionEntity position) {
        return super.save(position);
    }

    @Override
    public EmployeePositionEntity update(final EmployeePositionEntity position) {
        return super.update(position);
    }

    @Override
    public void delete(final Long id) {
        super.delete(id);
    }
}
