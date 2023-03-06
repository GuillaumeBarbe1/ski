package net.ent.etrs.ski.model.daos.impl;

import net.ent.etrs.ski.model.daos.DaoForfait;
import net.ent.etrs.ski.model.daos.JpaBaseDao;
import net.ent.etrs.ski.model.entities.Forfait;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DaoForfaitImpl extends JpaBaseDao<Forfait, Serializable> implements DaoForfait {


    @Override
    public List<Forfait> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy) {
        String sql = "SELECT p FROM Forfait p WHERE 1=1 ";

        String nom = null;
        BigDecimal prixJournalier = null;


        // filterBy containsKey
        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if (filterBy.containsKey("prixJournalier")) {
            prixJournalier = new BigDecimal(filterBy.get("prixJournalier"));
        }


        // test null + test equals or contains (LIKE)
        if (nom != null) {
            sql += " AND LOWER(p.nom) LIKE :nom ";
        }

        if (prixJournalier != null) {
            sql += " AND p.prixJournalier = :prixJournalier ";
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
        TypedQuery<Forfait> q = this.em.createQuery(sql, Forfait.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() + "%");
        }

        if (prixJournalier != null) {
            q.setParameter("prixJournalier", prixJournalier);
        }


        q.setFirstResult(first);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }

    @Override
    public int count(Map<String, String> filterBy) {
        String sql = "SELECT COUNT(p) FROM Forfait p WHERE 1=1 ";

        String nom = null;
        BigDecimal prixJournalier = null;

        // filterBy containsKey
        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if (filterBy.containsKey("prixJournalier")) {
            prixJournalier = new BigDecimal(filterBy.get("prixJournalier"));
        }


        // test null + test equals or contains (LIKE)
        if (nom != null) {
            sql += " AND LOWER(p.nom) LIKE :nom ";
        }

        if (prixJournalier != null) {
            sql += " AND p.prixJournalier = :prixJournalier ";
        }

        // TypedQuerry
        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() + "%");
        }

        if (prixJournalier != null) {
            q.setParameter("prixJournalier", prixJournalier);
        }

        return q.getSingleResult().intValue();
    }
}
