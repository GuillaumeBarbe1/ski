package net.ent.etrs.ski.model.daos;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.entities.Station;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface DaoRemontee extends BaseDao<Remontee, Serializable> {
    List<Remontee> findAllDispo() throws DaoException;

    Iterable<Remontee> findAllByPiste(Long id) throws DaoException;
}
