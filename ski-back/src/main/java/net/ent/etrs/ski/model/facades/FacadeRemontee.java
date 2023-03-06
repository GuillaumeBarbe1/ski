package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoRemontee;
import net.ent.etrs.ski.model.entities.Remontee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Local
@Stateless
public class FacadeRemontee {
    private static Log log = LogFactory.getLog("LoggerInit");

    @Inject
    private DaoRemontee remonteeDao;

    public Optional<Remontee> find(Long id) throws BusinessException {
        try {
            return this.remonteeDao.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Iterable<Remontee> findAll() throws BusinessException {
        try {
            return this.remonteeDao.findAll();
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Optional<Remontee> save(Remontee remontee) throws BusinessException {
        try {
            log.info("Enregistrement correctement effectué");
            return this.remonteeDao.save(remontee);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void delete(Long id) throws BusinessException {
        try {
            this.remonteeDao.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public boolean exists(Long id) throws BusinessException {
        try {
            return this.remonteeDao.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public long count() throws BusinessException {
        try {
            return this.remonteeDao.count();
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void deleteAll(List<Remontee> remontees) throws BusinessException {
        for (Remontee p : remontees) {
            this.delete(p.getId());
        }
    }

    public List<Remontee> findAllDispo() throws BusinessException {
        try {
            return this.remonteeDao.findAllDispo();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public List<Remontee> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy) throws BusinessException {
        return this.remonteeDao.load(first, pageSize, sortBy, filterBy);
    }

    public int count(Map<String, String> filterBy) throws BusinessException {
        return this.remonteeDao.count(filterBy);
    }


    public Iterable<Remontee> findAllByPiste(Long id) throws BusinessException {
        try {
            return this.remonteeDao.findAllByPiste(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
}
