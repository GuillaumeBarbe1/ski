package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.daos.DaoPiste;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.Piste;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Local
@Stateless
public class FacadePiste {
    
    @Inject
    private DaoPiste daoPiste;
    
    public Optional<Piste> find(Long id) throws BusinessException {
        try {
            return this.daoPiste.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public Iterable<Piste> findAll() throws BusinessException {
        try {
            return this.daoPiste.findAll();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public Optional<Piste> save(Piste piste) throws BusinessException {
        try {
            return this.daoPiste.save(piste);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public void delete(Long id) throws BusinessException {
        try {
            this.daoPiste.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public boolean exists(Long id) throws BusinessException {
        try {
            return this.daoPiste.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public long count() throws BusinessException {
        try {
            return this.daoPiste.count();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public void deleteAll(List<Piste> pistes) throws BusinessException {
        for(Piste p : pistes){
            this.delete(p.getId());
        }
    }
    
    public List<Piste> findAllDispo() throws BusinessException {
        try {
            return this.daoPiste.findAllDispo();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public List<Piste> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy) {
        return this.daoPiste.load(first, pageSize, sortBy, filterBy);
    }
    
    public int count(Map<String, String> filterBy) {
        return this.daoPiste.count(filterBy);
    }
}
