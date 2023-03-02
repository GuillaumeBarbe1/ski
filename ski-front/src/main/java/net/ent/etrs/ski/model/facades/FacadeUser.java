package net.ent.etrs.ski.model.facades;

import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.User;

import java.io.Serializable;
import java.util.Optional;

public class FacadeUser implements Serializable {
    public User findByLoginPassword(String login, String password) throws BusinessException {
        return null;
    }

    
    public Iterable<User> findAll() throws BusinessException {
        return null;
    }
    
    public Optional<User> save(User user) throws BusinessException {
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

}
