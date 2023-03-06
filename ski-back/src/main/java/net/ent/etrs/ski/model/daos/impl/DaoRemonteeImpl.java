package net.ent.etrs.ski.model.daos.impl;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoRemontee;
import net.ent.etrs.ski.model.daos.JpaBaseDao;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.entities.references.Etat;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DaoRemonteeImpl extends JpaBaseDao<Remontee, Serializable> implements DaoRemontee {
    @Override
    public List<Remontee> findAllDispo() throws DaoException {
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
        String sql = "SELECT p FROM Remontee p WHERE 1=1 ";

        String nom = null;
        Integer nbPlaceUnite = null;
        Etat etat = null;

        // filterBy containsKey
        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if (filterBy.containsKey("nbPlaceUnite")) {
            nbPlaceUnite = Integer.valueOf(filterBy.get("nbPlaceUnite"));
        }

        if (filterBy.containsKey("etat")) {
            etat = Etat.valueOf(filterBy.get("etat"));
        }

        // test null + test equals or contains (LIKE)
        if (nom != null) {
            sql += " AND LOWER(p.nom) LIKE :nom ";
        }

        if (nbPlaceUnite != null) {
            sql += " AND p.nbPlaceUnite = :nbPlaceUnite ";
        }

        if (etat != null) {
            sql += " AND p.etat = :etat ";
        }

        // OrderBy
        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for (Map.Entry<String, String> sort : sortBy.entrySet()) {
                sql += " p." + sort.getKey() + " " + sort.getValue() + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
        } else {
            // set default orderBy
            sql += " ORDER BY p.nom DESC ";
        }

        // TypedQuerry
        TypedQuery<Remontee> q = this.em.createQuery(sql, Remontee.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() + "%");
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
        String sql = "SELECT COUNT(p) FROM Remontee p WHERE 1=1 ";

        String nom = null;
        Integer nbPlaceUnite = null;
        Etat etat = null;

        // filterBy containsKey
        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if (filterBy.containsKey("nbPlaceUnite")) {
            nbPlaceUnite = Integer.valueOf(filterBy.get("nbPlaceUnite"));
        }

        if (filterBy.containsKey("etat")) {
            etat = Etat.valueOf(filterBy.get("etat"));
        }

        // test null + test equals or contains (LIKE)
        if (nom != null) {
            sql += " AND LOWER(p.nom) LIKE :nom ";
        }

        if (nbPlaceUnite != null) {
            sql += " AND p.nbPlaceUnite = :nbPlaceUnite ";
        }

        if (etat != null) {
            sql += " AND p.etat = :etat ";
        }

        // TypedQuerry
        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() + "%");
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
