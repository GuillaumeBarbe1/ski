package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Station;

import java.io.Serializable;
import java.util.Optional;

public class FacadeStation implements Serializable {

    public Optional<Station> find(Long id) throws BusinessException {
        return null;
    }


    public Iterable<Station> findAll() throws BusinessException {
        return null;
    }


    public Optional<Station> save(Station station) throws BusinessException {
        return null;
    }


    public void delete(Long id) throws BusinessException {
    }


    public boolean exists(Long id) throws BusinessException {
        return true;
    }


    public long count() throws BusinessException {
        return 0;
    }
    
    public Optional<Station> findByIdWithPistes(Long id) {
        return null;
    }

    public Long findByVille(String ville) throws BusinessException {
        return null;
    }
}
