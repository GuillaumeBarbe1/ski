package net.ent.etrs.ski.model.daos;



import net.ent.etrs.ski.exceptions.DaoException;

import java.io.Serializable;
import java.util.Optional;

public interface BaseDao<T, ID extends Serializable> {

    Optional<T> find(ID id) throws DaoException;

    Iterable<T> findAll() throws DaoException;

    Optional<T> save(T entity) throws DaoException;

    void delete(ID id) throws DaoException;

    boolean exists(ID id) throws DaoException;

    long count() throws DaoException;
}
