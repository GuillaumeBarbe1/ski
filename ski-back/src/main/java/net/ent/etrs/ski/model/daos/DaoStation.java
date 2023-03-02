package net.ent.etrs.ski.model.daos;

import net.ent.etrs.ski.model.entities.Station;

import java.io.Serializable;
import java.util.Optional;

public interface DaoStation extends BaseDao<Station, Serializable> {
    
    public Optional<Station> findByIdWithPistes(Long id);

    public Long findByVille(String ville);

}
