package com.azakharov.employeeapp.repository.eclipselink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public abstract class BaseEclipseLinkRepository<E, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEclipseLinkRepository.class);

    protected final EntityManager entityManager;

    private final Class<E> entityClass;

    public BaseEclipseLinkRepository(final EntityManager entityManager,
                                     final Class<E> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    protected Optional<E> find(final ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    protected List<E> findAll() {
        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(entityClass);
        final var rootEntry = query.from(entityClass);
        final var selectAllQuery = query.select(rootEntry);

        return entityManager.createQuery(selectAllQuery).getResultList();
    }

    protected E save(final E entity) {
        return upsert(entity);
    }

    protected E update(final E entity) {
        return upsert(entity);
    }

    protected void delete(final ID id) {
        find(id).ifPresentOrElse(this::processDelete, processDeletingFailing(id));
    }

    private E upsert(final E entity) {
        entityManager.getTransaction().begin();
        final var saved = entityManager.merge(entity);
        entityManager.getTransaction().commit();

        return saved;
    }

    private void processDelete(final E entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    private Runnable processDeletingFailing(final ID id) {
        return () -> {
            LOGGER.debug("Entity with id {} wasn''t found for deleting in database", id);
            throw new EclipseLinkRepositoryException("Entity with id {0} wasn''t found for deleting in database", id);
        };
    }
}
