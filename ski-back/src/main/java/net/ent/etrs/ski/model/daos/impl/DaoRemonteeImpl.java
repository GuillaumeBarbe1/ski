package net.ent.etrs.ski.model.daos.impl;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoForfait;
import net.ent.etrs.ski.model.daos.DaoRemontee;
import net.ent.etrs.ski.model.daos.JpaBaseDao;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class DaoRemonteeImpl extends JpaBaseDao<Remontee, Serializable> implements DaoRemontee
{
    @Override
    public List<Remontee> findAllDispo() throws DaoException
    {
        TypedQuery<Remontee> q = this.em.createQuery("SELECT r FROM Remontee r WHERE r NOT IN (" +
                "SELECT r FROM Piste p INNER JOIN p.remontees r" +
                ") ORDER BY r.nom DESC", Remontee.class);
        return q.getResultList();
    }

    @Override
    public Iterable<Remontee> findAllByPiste(Long id) throws DaoException {
        try {
            return this.em.createQuery("SELECT r FROM Remontee r WHERE r IN (SELECT r FROM Piste p LEFT JOIN p.remontees r WHERE p.id = :id)", Remontee.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
