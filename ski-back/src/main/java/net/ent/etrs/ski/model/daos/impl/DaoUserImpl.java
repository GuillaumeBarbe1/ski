package net.ent.etrs.ski.model.daos.impl;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoUser;
import net.ent.etrs.ski.model.daos.JpaBaseDao;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.entities.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Optional;


public class DaoUserImpl extends JpaBaseDao<User, Serializable> implements DaoUser {

    @Override
    public User findByLoginPasswd(String login, String password) throws DaoException {

            TypedQuery<User> q = this.em.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password =:passwd", User.class);
            q.setParameter("passwd", password);
            q.setParameter("login",login);
            return q.getSingleResult();
    }
}
