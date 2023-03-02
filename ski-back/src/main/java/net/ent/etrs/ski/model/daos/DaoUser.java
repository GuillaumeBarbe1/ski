package net.ent.etrs.ski.model.daos;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.User;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DaoUser extends BaseDao<User, Serializable> {

    User findByLoginPasswd(String login, String password ) throws DaoException;

}
