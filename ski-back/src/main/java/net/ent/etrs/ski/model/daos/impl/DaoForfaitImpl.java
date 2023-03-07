package net.ent.etrs.ski.model.daos.impl;

import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.daos.DaoForfait;
import net.ent.etrs.ski.model.daos.DaoPiste;
import net.ent.etrs.ski.model.daos.JpaBaseDao;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.entities.references.Niveau;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DaoForfaitImpl extends JpaBaseDao<Forfait, Serializable> implements DaoForfait
{


    @Override
    public Iterable<Forfait> findAllByStation(Long id) throws DaoException {
        try {
            return this.em.createQuery("SELECT f FROM Forfait f WHERE f IN (SELECT f FROM Station s LEFT JOIN s.lstForfaits f WHERE s.id = :id)", Forfait.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Forfait> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy) {
        String sql = "SELECT f FROM Forfait f WHERE 1=1 ";

        String nom = null;
        BigDecimal prixJournalier = null;

        if(filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if(filterBy.containsKey("prixJournalier")) {
            prixJournalier = new BigDecimal(filterBy.get("prixJournalier"));
        }


        if (nom != null) {
            sql += " AND LOWER(f.nom) LIKE :nom ";
        }

        if (prixJournalier != null) {
            sql += " AND f.prixJournalier = :prixJournalier ";

        }


        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for(Map.Entry<String, String> sort : sortBy.entrySet()) {
                sql += " f." + sort.getKey()+ " " + sort.getValue() + ",";
            }
            sql = sql.substring(0, sql.length() -1);
        } else {
            sql += " ORDER BY f.nom DESC ";
        }

        TypedQuery<Forfait> q = this.em.createQuery(sql, Forfait.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() +"%");
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
        String sql = "SELECT COUNT(f) FROM Forfait f WHERE 1=1 ";

        String nom = null;
        BigDecimal prixJournalier = null;

        if(filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if(filterBy.containsKey("prixJournalier")) {
            prixJournalier = new BigDecimal(filterBy.get("prixJournalier"));
        }


        if (nom != null) {
            sql += " AND LOWER(f.nom) LIKE :nom ";
        }

        if (prixJournalier != null) {
            sql += " AND f.prixJournalier = :prixJournalier ";

        }


        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() +"%");
        }

        if (prixJournalier != null) {
            q.setParameter("prixJournalier", prixJournalier);
        }


        return q.getSingleResult().intValue();
    }
}
