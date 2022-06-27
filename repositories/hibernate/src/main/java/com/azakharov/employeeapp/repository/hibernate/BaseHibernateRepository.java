package com.azakharov.employeeapp.repository.hibernate;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public abstract class BaseHibernateRepository<E, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHibernateRepository.class);

    protected final Session session;

    private final Class<E> entityClass;

    public BaseHibernateRepository(final Session session,
                                   final Class<E> entityClass) {
        this.session = session;
        this.entityClass = entityClass;
    }

    protected List<E> findAll() {
        final var criteriaBuilder = session.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(entityClass);
        final var rootEntry = query.from(entityClass);
        final var selectAllQuery = query.select(rootEntry);

        return session.createQuery(selectAllQuery).getResultList();
    }

    protected Optional<E> find(final ID id) {
        return Optional.ofNullable(session.find(entityClass, id));
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
        session.getTransaction().begin();
        final var saved = session.merge(entity);
        session.getTransaction().commit();

        return saved;
    }

    private void processDelete(final E entity) {
        session.getTransaction().begin();
        session.remove(entity);
        session.getTransaction().commit();
    }

    private Runnable processDeletingFailing(final ID id) {
        return () -> {
            LOGGER.debug("Entity with id {} wasn''t found for deleting in database", id);
            throw new HibernateRepositoryException("Entity with id {0} wasn''t found for deleting in database", id);
        };
    }
}
