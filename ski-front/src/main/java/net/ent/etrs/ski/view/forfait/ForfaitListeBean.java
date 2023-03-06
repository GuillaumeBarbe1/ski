package net.ent.etrs.ski.view.forfait;


import lombok.Getter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.facades.FacadeForfait;
import net.ent.etrs.ski.utils.JsfUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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
