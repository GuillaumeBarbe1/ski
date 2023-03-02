package net.ent.etrs.ski.model.daos.impl;

import net.ent.etrs.ski.model.daos.DaoStation;
import net.ent.etrs.ski.model.daos.JpaBaseDao;
import net.ent.etrs.ski.model.entities.Station;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
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
}
