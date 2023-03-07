package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoUser;
import net.ent.etrs.ski.model.entities.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Local
@Stateless
public class FacadeUser implements Serializable {

    private static Log log = LogFactory.getLog("LoggerInit");

    @Inject
    private DaoUser userDao;

    public Optional<User> find(Long id) throws BusinessException {
        try {
            return this.userDao.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Iterable<User> findAll() throws BusinessException {
        try {
            return this.userDao.findAll();
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Optional<User> save(User user) throws BusinessException {
        try {
            log.info("Enregistrement correctement effectué");
            return this.userDao.save(user);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void delete(Long id) throws BusinessException {
        try {
            this.userDao.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public boolean exists(Long id) throws BusinessException {
        try {
            return this.userDao.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public long count() throws BusinessException {
        try {
            return this.userDao.count();
        } catch (DaoException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void deleteAll(List<User> users) throws BusinessException {
        for (User p : users) {
            this.delete(p.getId());
        }
    }


    public User findByLoginPassword(String login, String password) throws BusinessException {
        try {
            return userDao.findByLoginPasswd(login, password);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public Optional<User> findByLogin(String login) throws BusinessException {
        return userDao.findByLogin(login);
    }

}
