package net.ent.etrs.ski.model.daos;


import lombok.Getter;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.Optional;

public class JpaBaseDao<T extends AbstractEntity, ID> implements BaseDao<T, Serializable>, Serializable {

    protected Class<T> entityClass;

    @Getter
    @PersistenceContext
    protected EntityManager em;

    public JpaBaseDao() {
        ParameterizedType genericSuperClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];
    }

    @Override
    public Optional<T> find(Serializable id) throws DaoException {
        try {
            return Optional.ofNullable(this.em.find(this.entityClass, id));
        } catch (IllegalArgumentException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public Iterable<T> findAll() throws DaoException {
        try {
            return this.em.createQuery("SELECT t FROM " + this.entityClass.getSimpleName() + " t", this.entityClass).getResultList();
        } catch (IllegalArgumentException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<T> save(T entity) throws DaoException {
        try {
            if (Objects.nonNull(entity.getId())) {
                return Optional.ofNullable(this.em.merge(entity));
            } else {
                this.em.persist(entity);
                return Optional.of(entity);
            }
        } catch (IllegalStateException | IllegalArgumentException | PersistenceException e) {
            throw new DaoException(e.getMessage(), e);
        }

    }

    @Override
    public void delete(Serializable id) throws DaoException {
        try {
            Query query = this.em.createQuery("DELETE FROM " + this.entityClass.getSimpleName() + " t WHERE t.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (IllegalStateException | IllegalArgumentException | PersistenceException e) {
            throw new DaoException(e.getMessage(), e);
        }

    }

    @Override
    public boolean exists(Serializable id) throws DaoException {
        return this.find(id).isPresent();
    }

    @Override
    public long count() throws DaoException {
        try {
            return this.em.createQuery("SELECT COUNT(t) FROM " + this.entityClass.getSimpleName() + " t", Long.class).getSingleResult();
        } catch (IllegalArgumentException | PersistenceException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}
