package net.ent.etrs.ski.view.station;


import lombok.Getter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.facades.FacadeStation;
import net.ent.etrs.ski.utils.JsfUtils;
import org.apache.commons.collections4.IterableUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@ViewScoped
public class StationListeBean implements Serializable {

    public final static String FLASH = "stations";

    @Inject
    private FacadeStation facadeStation;

    @Getter
    private List<Station> stationList;

    @PostConstruct
    public void init() {
        try {
            this.stationList = IterableUtils.toList(this.facadeStation.findAll());
        } catch (BusinessException e)
        {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur chargement metier");
        }
    }

    public void supprimer(Long id) throws Exception {
        try {
            this.facadeStation.delete(id);
        } catch (BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur suppression metier");
        }
        JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "La station a bien été supprimé !");
        this.init();
    }

    public void modifier(Long id) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(this.FLASH, id);
    }

    public List<Etat> getEtats()
    {
        return Arrays.asList(Etat.values());
    }
}
