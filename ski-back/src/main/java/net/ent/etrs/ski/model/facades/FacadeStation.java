package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.daos.DaoStation;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.Station;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Local
@Stateless
public class FacadeStation {

    @Inject
    private DaoStation daoStation;

    public Optional<Station> find(Long id) throws BusinessException {
        try {
            return this.daoStation.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }


    public Iterable<Station> findAll() throws BusinessException {
        try {
            return this.daoStation.findAll();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }


    public Optional<Station> save(Station station) throws BusinessException {
        try {
            return this.daoStation.save(station);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }


    public void delete(Long id) throws BusinessException {
        Station s = this.find(id).get();
        s.setPistes(new ArrayList<>());
        this.save(s);
        try {
            this.daoStation.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }


    public boolean exists(Long id) throws BusinessException {
        try {
            return this.daoStation.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }


    public long count() throws BusinessException {
        try {
            return this.daoStation.count();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public Optional<Station> findByIdWithPistes(Long id) {
        return this.daoStation.findByIdWithPistes(id);
    }

    public Long findByVille(String ville) throws BusinessException {
        return this.daoStation.findByVille(ville);
    }
}
