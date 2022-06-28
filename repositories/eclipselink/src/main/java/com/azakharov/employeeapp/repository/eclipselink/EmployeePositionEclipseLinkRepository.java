package com.azakharov.employeeapp.repository.eclipselink;

import com.azakharov.employeeapp.repository.jpa.EmployeePositionRepository;
import com.azakharov.employeeapp.repository.jpa.entity.EmployeePositionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class EmployeePositionEclipseLinkRepository extends BaseEclipseLinkRepository<EmployeePositionEntity, Long>
        implements EmployeePositionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePositionEclipseLinkRepository.class);

    @Inject
    public EmployeePositionEclipseLinkRepository(final EntityManager entityManager) {
        super(entityManager, EmployeePositionEntity.class);
    }

    @Override
    public Optional<EmployeePositionEntity> find(final Long id) {
        LOGGER.debug("Finding EmployeePositionEntity started, id: {}", id);
        final var position = super.find(id);
        LOGGER.trace("EmployeePositionEntity detailed printing: {}", position);

        return position;
    }

    @Override
    public List<EmployeePositionEntity> findAll() {
        LOGGER.debug("Finding all EmployeePositionEntities started");
        final var positions = super.findAll();
        LOGGER.trace("EmployeePositionEntities detailed printing: {}", positions);

        return positions;
    }

    @Override
    public EmployeePositionEntity save(final EmployeePositionEntity position) {
        LOGGER.debug("Saving EmployeePositionEntity started, position: {}", position);
        final var saved = super.save(position);
        LOGGER.debug("EmployeePositionEntity saving successfully ended, generated id: {}", saved.getId());

        return saved;
    }

    @Override
    public EmployeePositionEntity update(final EmployeePositionEntity position) {
        LOGGER.debug("EmployeePositionEntity updating started, position: {}", position);
        return super.update(position);
    }

    @Override
    public void delete(final Long id) {
        LOGGER.debug("EmployeePositionEntity deleting started, id: {}", id);
        super.delete(id);
    }
}