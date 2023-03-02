package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoForfait;
import net.ent.etrs.ski.model.entities.Forfait;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Local
@Stateless
public class FacadeForfait
{
    
    @Inject
    private DaoForfait daoForfait;
    
    public Optional<Forfait> find(Long id) throws BusinessException {
        try {
            return this.daoForfait.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public Iterable<Forfait> findAll() throws BusinessException {
        try {
            return this.daoForfait.findAll();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public Optional<Forfait> save(Forfait forfait) throws BusinessException {
        try {
            return this.daoForfait.save(forfait);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public void delete(Long id) throws BusinessException {
        try {
            this.daoForfait.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public boolean exists(Long id) throws BusinessException {
        try {
            return this.daoForfait.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public long count() throws BusinessException {
        try {
            return this.daoForfait.count();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }


}
