package net.ent.etrs.ski.model.daos;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.Piste;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DaoPiste extends BaseDao<Piste, Serializable> {
    List<Piste> findAllDispo() throws DaoException;
    
    List<Piste> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);
    
    int count(Map<String, FilterMeta> filterBy);
}
