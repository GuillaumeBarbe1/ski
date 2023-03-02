package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoPiste;
import net.ent.etrs.ski.model.daos.DaoUser;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.User;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Local
@Stateless
public class FacadeUser implements Serializable {
    
    @Inject
    private DaoUser daoUser;


    public User findByLoginPassword(String login, String password) throws BusinessException {
        try {
            return daoUser.findByLoginPasswd(login,password);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    
    public Iterable<User> findAll() throws BusinessException {
        try {
            return this.daoUser.findAll();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public Optional<User> save(User user) throws BusinessException {
        try {
            return this.daoUser.save(user);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public void delete(Long id) throws BusinessException {
        try {
            this.daoUser.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public boolean exists(Long id) throws BusinessException {
        try {
            return this.daoUser.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
    
    public long count() throws BusinessException {
        try {
            return this.daoUser.count();
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

}
