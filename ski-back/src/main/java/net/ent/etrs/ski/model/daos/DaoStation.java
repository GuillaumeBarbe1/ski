package net.ent.etrs.ski.model.daos;

import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Station;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DaoStation extends BaseDao<Station, Serializable> {
    
    public Optional<Station> findByIdWithPistes(Long id);

    public Long findByVille(String ville);

    List<Station> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy);

    int count(Map<String, String> filterBy);

}
