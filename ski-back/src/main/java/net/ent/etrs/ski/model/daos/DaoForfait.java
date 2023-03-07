package net.ent.etrs.ski.model.daos;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DaoForfait extends BaseDao<Forfait, Serializable> {


    Iterable<Forfait> findAllByStation(Long id) throws DaoException;

    List<Forfait> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy);

    int count(Map<String, String> filterBy);
}
