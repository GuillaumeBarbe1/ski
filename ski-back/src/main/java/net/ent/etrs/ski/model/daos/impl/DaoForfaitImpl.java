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
import java.util.List;
import java.util.Map;

public class DaoForfaitImpl extends JpaBaseDao<Forfait, Serializable> implements DaoForfait
{
    

}
