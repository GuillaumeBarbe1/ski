package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoStation;
import net.ent.etrs.ski.model.entities.Station;
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
public class FacadeStation {

    private static Log log = LogFactory.getLog("LoggerInit");

    @Inject
    private DaoStation stationDao;

    public Optional<Station> find(Long id) throws BusinessException {
        try {
            return this.stationDao.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Iterable<Station> findAll() throws BusinessException {
        try {
            return this.stationDao.findAll();
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Optional<Station> save(Station station) throws BusinessException {
        try {
            log.info("Enregistrement correctement effectué");
            return this.stationDao.save(station);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void delete(Long id) throws BusinessException {
        try {
            this.stationDao.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public boolean exists(Long id) throws BusinessException {
        try {
            return this.stationDao.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public long count() throws BusinessException {
        try {
            return this.stationDao.count();
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void deleteAll(List<Station> stations) throws BusinessException {
        for (Station p : stations) {
            this.delete(p.getId());
        }
    }

    public List<Station> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy) throws BusinessException {
        return this.stationDao.load(first, pageSize, sortBy, filterBy);
    }

    public int count(Map<String, String> filterBy) throws BusinessException {
        return this.stationDao.count(filterBy);
    }


    public Optional<Station> findByIdWithPistes(Long id) {
        return this.stationDao.findByIdWithPistes(id);
    }

    public Long findByVille(String ville) throws BusinessException {
        return this.stationDao.findByVille(ville);
    }
}
