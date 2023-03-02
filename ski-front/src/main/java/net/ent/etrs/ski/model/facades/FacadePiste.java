package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacadePiste implements Serializable {
    
    public Optional<Piste> find(Long id) throws BusinessException {
        return null;
    }
    
    public Iterable<Piste> findAll() throws BusinessException {
        return null;
    }
    
    public Optional<Piste> save(Piste piste) throws BusinessException {
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

    public void deleteAll(List<Piste> pistes) throws BusinessException {
    }
    
    public List<Piste> findAllDispo() throws BusinessException {
        return null;
    }
    
    public List<Piste> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return null;
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        return 0;
    }
}
