package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoForfait;
import net.ent.etrs.ski.model.entities.Forfait;
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
public class FacadeForfait {

    private static Log log = LogFactory.getLog("LoggerInit");

    @Inject
    private DaoForfait forfaitDao;

    public Optional<Forfait> find(Long id) throws BusinessException {
        try {
            return this.forfaitDao.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Iterable<Forfait> findAll() throws BusinessException {
        try {
            return this.forfaitDao.findAll();
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Optional<Forfait> save(Forfait forfait) throws BusinessException {
        try {
            log.info("Enregistrement correctement effectué");
            return this.forfaitDao.save(forfait);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void delete(Long id) throws BusinessException {
        try {
            this.forfaitDao.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public boolean exists(Long id) throws BusinessException {
        try {
            return this.forfaitDao.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public long count() throws BusinessException {
        try {
            return this.forfaitDao.count();
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void deleteAll(List<Forfait> forfaits) throws BusinessException {
        for(Forfait p : forfaits){
            this.delete(p.getId());
        }
    }


    public List<Forfait> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy) {
        return this.forfaitDao.load(first, pageSize, sortBy, filterBy);
    }

    public int count(Map<String, String> filterBy) {
        return this.forfaitDao.count(filterBy);
    }



}
