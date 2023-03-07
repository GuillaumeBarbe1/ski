package net.ent.etrs.ski.model.daos;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.User;

import java.io.Serializable;
import java.util.Optional;

public interface DaoUser extends BaseDao<User, Serializable> {

    User findByLoginPasswd(String login, String password ) throws DaoException;
    
    Optional<User> findByLogin(String login);
}
