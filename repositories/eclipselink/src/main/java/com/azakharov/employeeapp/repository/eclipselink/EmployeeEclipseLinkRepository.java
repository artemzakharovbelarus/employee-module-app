package com.azakharov.employeeapp.repository.eclipselink;

import com.azakharov.employeeapp.repository.jpa.EmployeeRepository;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class EmployeeEclipseLinkRepository extends BaseEclipseLinkRepository<EmployeeEntity, Long> implements EmployeeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeEclipseLinkRepository.class);

    @Inject
    public EmployeeEclipseLinkRepository(final EntityManager entityManager) {
        super(entityManager, EmployeeEntity.class);
    }

    @Override
    public Optional<EmployeeEntity> find(final Long id) {
        LOGGER.debug("Finding EmployeeEntity in database started for id: {}", id);
        final var employee = super.find(id);
        LOGGER.trace("EmployeeEntity detailed printing: {}", employee);

        return employee;
    }

    @Override
    public List<EmployeeEntity> findAll() {
        LOGGER.debug("Finding all EmployeeEntity in database started");
        final var employees = super.findAll();
        LOGGER.trace("EmployeeEntity detailed printing: {}", employees);

        return employees;
    }

    @Override
    public EmployeeEntity save(final EmployeeEntity employee) {
        LOGGER.debug("EmployeeEntity saving started, position: {}", employee);
        final var saved = super.save(employee);
        LOGGER.debug("EmployeeEntity saving successfully ended, generated id: {}", saved.getId());

        return saved;
    }

    @Override
    public EmployeeEntity update(final EmployeeEntity employee) {
        LOGGER.debug("EmployeeEntity updating started, position: {}", employee);
        return super.update(employee);
    }

    @Override
    public void delete(final Long id) {
        LOGGER.debug("EmployeeEntity deleting started, id: {}", id);
        super.delete(id);
    }
}
