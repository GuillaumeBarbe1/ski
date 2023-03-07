package net.ent.etrs.ski.model.daos;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DaoPiste extends BaseDao<Piste, Serializable> {
    List<Piste> findAllDispo() throws DaoException;
    
    List<Piste> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy);
    
    int count(Map<String, String> filterBy);

    Iterable<Piste> findAllByStation(Long id) throws DaoException;

    Iterable<Piste> findAllByForfait(Long id) throws DaoException;
}
