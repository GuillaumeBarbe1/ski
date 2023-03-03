package net.ent.etrs.ski.model.daos.impl;

import net.ent.etrs.ski.model.daos.DaoPiste;
import net.ent.etrs.ski.model.daos.JpaBaseDao;
import net.ent.etrs.ski.exceptions.DaoException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.entities.references.Niveau;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.*;

public class DaoPisteImpl extends JpaBaseDao<Piste, Serializable> implements DaoPiste {
    
    @Override
    public List<Piste> findAllDispo() throws DaoException {
        TypedQuery<Piste> q = this.em.createQuery("SELECT p FROM Piste p WHERE p NOT IN (" +
                    "SELECT p FROM Station s INNER JOIN s.pistes p" +
                ") ORDER BY p.nom DESC", Piste.class);
        return q.getResultList();
    }
    
    @Override
    public List<Piste> load(int first, int pageSize, Map<String, String> sortBy, Map<String, String> filterBy) {
        String sql = "SELECT p FROM Piste p WHERE 1=1 ";
        
        String nom = null;
        Etat etat = null;
        Niveau niveau = null;
        Integer longueur = null;
        Double penteMoy = null;
        
        if(filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }
    
        if(filterBy.containsKey("etat")) {
           etat = Etat.valueOf(filterBy.get("etat"));
        }
    
        if(filterBy.containsKey("niveau")) {
            niveau = Niveau.valueOf(filterBy.get("niveau"));
        }
    
        if(filterBy.containsKey("longueur")) {
            longueur = Integer.valueOf(filterBy.get("longueur"));
        }
    
        if(filterBy.containsKey("penteMoy")) {
            penteMoy = Double.valueOf(filterBy.get("penteMoy"));
        }
        
        if (nom != null) {
            sql += " AND LOWER(p.nom) LIKE :nom ";
        }
    
        if (etat != null) {
            sql += " AND p.etat = :etat ";
        
        }
    
        if (niveau != null) {
            sql += " AND p.niveau = :niveau ";
        }
    
        if (longueur != null) {
            sql += " AND p.longueur = :longueur ";
        }
    
        if (penteMoy != null) {
            sql += " AND p.penteMoy = :penteMoy ";
        }
    
        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for(Map.Entry<String, String> sort : sortBy.entrySet()) {
                sql += " p." + sort.getValue() + " " + sort.getValue() + ",";
            }
            sql = sql.substring(0, sql.length() -1);
        } else {
            sql += " ORDER BY p.nom DESC ";
        }
        
        TypedQuery<Piste> q = this.em.createQuery(sql, Piste.class);
    
        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() +"%");
        }
    
        if (etat != null) {
            q.setParameter("etat", etat);
        }
    
        if (niveau != null) {
            q.setParameter("niveau", niveau);
        }
    
        if (longueur != null) {
            q.setParameter("longueur", longueur);
        }
    
        if (penteMoy != null) {
            q.setParameter("penteMoy", penteMoy);
        }
        
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        
        return q.getResultList();
    }
    
    @Override
    public int count(Map<String, String> filterBy) {
        String sql = "SELECT COUNT(p) FROM Piste p WHERE 1=1 ";
    
        String nom = null;
        Etat etat = null;
        Niveau niveau = null;
        Integer longueur = null;
        Double penteMoy = null;
    
        if(filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }
    
        if(filterBy.containsKey("etat")) {
            etat =  Etat.valueOf(filterBy.get("etat"));
        }
    
        if(filterBy.containsKey("niveau")) {
            niveau = Niveau.valueOf(filterBy.get("niveau"));
        }
    
        if(filterBy.containsKey("longueur")) {
            longueur = Integer.valueOf(filterBy.get("longueur"));
        }
    
        if(filterBy.containsKey("penteMoy")) {
            penteMoy = Double.valueOf(filterBy.get("penteMoy"));
        }
    
        if (nom != null) {
            sql += " AND LOWER(p.nom) LIKE :nom ";
        }
    
        if (etat != null) {
            sql += " AND p.etat = :etat ";
        
        }
    
        if (niveau != null) {
            sql += " AND p.niveau = :niveau ";
        }
    
        if (longueur != null) {
            sql += " AND p.longueur = :longueur ";
        }
    
        if (penteMoy != null) {
            sql += " AND p.penteMoy = :penteMoy ";
        }
    
        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);
    
        if (nom != null) {
            q.setParameter("nom", nom.toLowerCase() +"%");
        }
    
        if (etat != null) {
            q.setParameter("etat", etat);
        }
    
        if (niveau != null) {
            q.setParameter("niveau", niveau);
        }
    
        if (longueur != null) {
            q.setParameter("longueur", longueur);
        }
    
        if (penteMoy != null) {
            q.setParameter("penteMoy", penteMoy);
        }
        return q.getSingleResult().intValue();
    }
}
