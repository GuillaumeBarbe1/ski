package net.ent.etrs.ski.view.forfait;


import lombok.Getter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.facades.FacadeForfait;
import net.ent.etrs.ski.model.facades.FacadeStation;
import net.ent.etrs.ski.utils.JsfUtils;
import org.apache.commons.collections4.IterableUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@ViewScoped
public class ForfaitListeBean implements Serializable {

    public final static String FLASH = "forfaits";
    @Inject
    private FacadeForfait facadeForfait;

    @Getter
    @Inject
    private LazyDataModelForfait forfaitList;

    @PostConstruct
    public void init() {
//        try {
//            this.forfaitList = IterableUtils.toList(this.facadeForfait.findAll());
//        } catch (BusinessException e)
//        {
//            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur chargement metier");
//        }
    }

    public void supprimer(Long id) throws Exception {
        try {
            this.facadeForfait.delete(id);
        } catch (BusinessException e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur suppression metier");
        }
        JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "Le forfait a bien été supprimé !");
        this.init();
    }

    public void modifier(Long id) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(this.FLASH, id);
    }

}
