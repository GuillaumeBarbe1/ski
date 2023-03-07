package net.ent.etrs.ski.model.daos.impl;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoForfait;
import net.ent.etrs.ski.model.daos.DaoRemontee;
import net.ent.etrs.ski.model.daos.JpaBaseDao;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.entities.references.Niveau;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
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

    @Override
    public List<Remontee> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy) {
        String sql = "SELECT r FROM Remontee r WHERE 1=1 ";

        String nom = null;
        Integer nbPlaceUnite = null;
        Etat etat = null;

        if(filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if(filterBy.containsKey("nbPlaceUnite")) {
            nbPlaceUnite = Integer.valueOf(filterBy.get("nbPlaceUnite"));
        }

        if(filterBy.containsKey("etat")) {
            etat = Etat.valueOf(filterBy.get("etat"));
        }


        if (nom != null) {
            sql += " AND LOWER(r.nom) LIKE :nom ";
        }

        if (nbPlaceUnite != null) {
            sql += " AND r.nbPlaceUnite = :nbPlaceUnite ";
        }

        if (etat != null) {
            sql += " AND r.etat = :etat ";
        }


        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for(Map.Entry<String, String> sort : sortBy.entrySet()) {
                sql += " r." + sort.getKey()+ " " + sort.getValue() + ",";
            }
            sql = sql.substring(0, sql.length() -1);
        } else {
            sql += " ORDER BY r.nom DESC ";
        }

        TypedQuery<Remontee> q = this.em.createQuery(sql, Remontee.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() +"%");
        }

        if (nbPlaceUnite != null) {
            q.setParameter("nbPlaceUnite", nbPlaceUnite);
        }

        if (etat != null) {
            q.setParameter("etat", etat);
        }


        q.setFirstResult(first);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }


    @Override
    public int count(Map<String, String> filterBy) {
        String sql = "SELECT COUNT(r) FROM Remontee r WHERE 1=1 ";

        String nom = null;
        Integer nbPlaceUnite = null;
        Etat etat = null;

        if(filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if(filterBy.containsKey("nbPlaceUnite")) {
            nbPlaceUnite = Integer.valueOf(filterBy.get("nbPlaceUnite"));
        }

        if(filterBy.containsKey("etat")) {
            etat = Etat.valueOf(filterBy.get("etat"));
        }


        if (nom != null) {
            sql += " AND LOWER(r.nom) LIKE :nom ";
        }

        if (nbPlaceUnite != null) {
            sql += " AND r.nbPlaceUnite = :nbPlaceUnite ";
        }

        if (etat != null) {
            sql += " AND r.etat = :etat ";
        }

        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() +"%");
        }

        if (nbPlaceUnite != null) {
            q.setParameter("nbPlaceUnite", nbPlaceUnite);
        }

        if (etat != null) {
            q.setParameter("etat", etat);
        }

        return q.getSingleResult().intValue();
    }

}
