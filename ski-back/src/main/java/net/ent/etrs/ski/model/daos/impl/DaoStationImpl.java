package net.ent.etrs.ski.model.daos.impl;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.ski.model.daos.DaoStation;
import net.ent.etrs.ski.model.daos.JpaBaseDao;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.entities.references.Etat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DaoStationImpl extends JpaBaseDao<Station, Serializable> implements DaoStation {
    
    @Override
    public Optional<Station> findByIdWithPistes(Long id) {
        try {
            TypedQuery<Station> q = this.em.createQuery("" +
                    "SELECT s FROM Station s LEFT JOIN FETCH s.pistes p WHERE s.id = :id" +
                    "", Station.class);
            q.setParameter("id", id);
            return Optional.of(q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    /**
     * Retourne l'id de la station qui à pour nom de ville le parametre ou retourne 0 si aucune station trouvée.
     * @param ville
     * @return id
     */
    public Long findByVille(String ville){
        Long retour;

        try
         {
             TypedQuery<Long> q = this.em.createQuery("SELECT s.id FROM Station s WHERE s.ville = :ville", Long.class);
             q.setParameter("ville", ville);
             retour = q.getSingleResult();
         }
         catch(NoResultException e)
        {
           retour= Long.valueOf(0);
        }
        return retour;
    }

    @Override
    public List<Station> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String > filterBy) {
        String sql = "SELECT p FROM Station p WHERE 1=1 ";

        String nom = null;
        String ville = null;
        Integer nbHabitants = null;
        Integer altitude = null;
        Etat etat = null;

        // filterBy containsKey
        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if (filterBy.containsKey("ville")) {
            ville = filterBy.get("ville");
        }

        if (filterBy.containsKey("nbHabitants")) {
            nbHabitants = Integer.valueOf(filterBy.get("nbHabitants"));
        }

        if (filterBy.containsKey("altitude")) {
            altitude = Integer.valueOf(filterBy.get("altitude"));
        }

        if (filterBy.containsKey("etat")) {
            etat = Etat.valueOf(filterBy.get("etat"));
        }

        // test null + test equals or contains (LIKE)
        if (nom != null) {
            sql += " AND LOWER(p.nom) LIKE :nom ";
        }

        if (ville != null) {
            sql += " AND LOWER(p.ville) LIKE :ville ";
        }

        if (nbHabitants != null) {
            sql += " AND p.nbHabitants = :nbHabitants ";
        }

        if (altitude != null) {
            sql += " AND p.altitude = :altitude ";
        }

        if (etat != null) {
            sql += " AND p.etat = :etat ";
        }

        // OrderBy
        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for (Map.Entry<String, String> sort : sortBy.entrySet()) {
                sql += " l." + sort.getKey() + " " + sort.getValue() + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
        } else {
            // set default orderBy
            sql += " ORDER BY p.nom DESC ";
        }

        // TypedQuerry
        TypedQuery<Station> q = this.em.createQuery(sql, Station.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() + "%");
        }

        if (ville != null) {
            q.setParameter("ville", ville.toLowerCase() + "%");
        }

        if (nbHabitants != null) {
            q.setParameter("nbHabitants", nbHabitants);
        }

        if (altitude != null) {
            q.setParameter("altitude", altitude);
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
        String sql = "SELECT COUNT(p) FROM Station p WHERE 1=1 ";

        String nom = null;
        String ville = null;
        Integer nbHabitants = null;
        Integer altitude = null;
        Etat etat = null;

        // filterBy containsKey
        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if (filterBy.containsKey("ville")) {
            ville = filterBy.get("ville");
        }

        if (filterBy.containsKey("nbHabitants")) {
            nbHabitants = Integer.valueOf(filterBy.get("nbHabitants"));
        }

        if (filterBy.containsKey("altitude")) {
            altitude = Integer.valueOf(filterBy.get("altitude"));
        }

        if (filterBy.containsKey("etat")) {
            etat = Etat.valueOf(filterBy.get("etat"));
        }

        // test null + test equals or contains (LIKE)
        if (nom != null) {
            sql += " AND LOWER(p.nom) LIKE :nom ";
        }

        if (ville != null) {
            sql += " AND LOWER(p.ville) LIKE :ville ";
        }

        if (nbHabitants != null) {
            sql += " AND p.nbHabitants = :nbHabitants ";
        }

        if (altitude != null) {
            sql += " AND p.altitude = :altitude ";
        }

        if (etat != null) {
            sql += " AND p.etat = :etat ";
        }

        // TypedQuerry
        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);

        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() + "%");
        }

        if (ville != null) {
            q.setParameter("ville", ville.toLowerCase() + "%");
        }

        if (nbHabitants != null) {
            q.setParameter("nbHabitants", nbHabitants);
        }

        if (altitude != null) {
            q.setParameter("altitude", altitude);
        }

        if (etat != null) {
            q.setParameter("etat", etat);
        }

        return q.getSingleResult().intValue();
    }
    
    
}
