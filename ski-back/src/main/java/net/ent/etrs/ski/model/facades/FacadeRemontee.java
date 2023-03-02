package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoRemontee;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Local
@Stateless
public class FacadeRemontee
{
    
    @Inject
    private DaoRemontee daoRemontee;
    
    public Optional<Remontee> find(Long id) throws BusinessException {
        try {
            return this.daoRemontee.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public Iterable<Remontee> findAll() throws BusinessException {
        try {
            return this.daoRemontee.findAll();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public Optional<Remontee> save(Remontee remontee) throws BusinessException {
        try {
            return this.daoRemontee.save(remontee);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public void delete(Long id) throws BusinessException {
        try {
            this.daoRemontee.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public boolean exists(Long id) throws BusinessException {
        try {
            return this.daoRemontee.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public long count() throws BusinessException {
        try {
            return this.daoRemontee.count();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public Iterable<Remontee> findAllByPisteId(Long idPiste) throws BusinessException {
        try {
            return this.daoRemontee.findAllByPisteId(idPiste);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
}
